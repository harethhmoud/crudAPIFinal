package com.example.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item(){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;


}
