package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private Long id;
    private String name;
    private String email;
    private Long salary;

    @PrePersist
    public void generateIdIfNotPresent() {
        if (this.id == null) {
            this.id = ThreadLocalRandom.current().nextLong(10000, 100000);
        }
    }
}
