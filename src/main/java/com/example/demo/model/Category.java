package com.example.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name="category")
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)  // Ensure enum is stored as String
    @Column(name = "name")
    private Categories name;

    @Column(name = "description")
    private String description;

    public Category(){}

    public Category(Categories name, String description){
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Categories getName() {
        return name;
    }

    public void setName(Categories name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public java.lang.String toString() {
        return "Category{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + description + '\'' +
                '}';
    }
}