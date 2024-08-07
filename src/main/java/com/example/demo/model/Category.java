package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor // lombok stuff
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)  // Ensure enum is stored as String
    @Column(name = "name")
    private Categories name;

    @Column(name = "description")
    private String description;
}