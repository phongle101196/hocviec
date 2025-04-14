package com.demo;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.ClassMeta;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.service.ValidationContext;
import com.demo.validation.service.ValidationModule;
import com.demo.validation.service.ValidatorFactory;
import com.demo.validation.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableKafka
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }
    @Bean
    public ValidationContext validationContext() throws IOException {
        ValidationContext context = new ValidationContext();

        // Load rules.json
        List<Map<String, Object>> rules = JsonUtils.loadJsonFile("src/main/resources/rules.json", new TypeReference<List<Map<String, Object>>>() {});

        // Create a set of class names and their validated fields from rules.json
        Map<String, Set<String>> classFieldsMap = rules.stream()
                .collect(Collectors.toMap(
                        rule -> (String) rule.get("className"),
                        rule -> ((List<String>) rule.get("validatedFields")).stream().collect(Collectors.toSet())
                ));

        // Load meta.json
        List<Map<String, Object>> meta = JsonUtils.loadJsonFile("src/main/resources/meta.json", new TypeReference<List<Map<String, Object>>>() {});

        // Process meta to create ClassMeta and FieldMeta objects based on rules
        for (Map<String, Object> classMetaMap : meta) {
            String springClassName = (String) classMetaMap.get("springClassName");
            String className = (String) classMetaMap.get("className");

            // Check if the class is in the rules
            if (classFieldsMap.containsKey(className)) {
                ClassMeta classMeta = new ClassMeta(springClassName, className);
                Set<String> validatedFields = classFieldsMap.get(className);

                List<Map<String, Object>> fields = (List<Map<String, Object>>) classMetaMap.get("fields");
                for (Map<String, Object> fieldMap : fields) {
                    String fieldName = (String) fieldMap.get("fieldName");

                    // Check if the field is in the validated fields
                    if (validatedFields.contains(fieldName)) {
                        String springFieldType = (String) fieldMap.get("springFieldType");
                        String fieldType = (String) fieldMap.get("fieldType");
                        FieldMeta fieldMeta = new FieldMeta(fieldName, springFieldType, fieldType);

                        List<Map<String, Object>> attributes = (List<Map<String, Object>>) fieldMap.get("attributes");
                        for (Map<String, Object> attributeMap : attributes) {
                            String type = (String) attributeMap.get("type");
                            Map<String, Object> params = (Map<String, Object>) attributeMap.get("params");
                            AttributeRule attributeRule = ValidatorFactory.getInstance().getAttributeRule(type, params);
                            if (attributeRule != null) {
                                fieldMeta.addAttribute(attributeRule);
                            }
                        }

                        classMeta.addField(fieldMeta);
                    }
                }

                context.addClassMeta(className, classMeta);
            }
        }

        return context;
    }

    @Bean
    public ValidationModule validationModule(ValidationContext context) {
        return new ValidationModule(context);
    }
}
