package com.example.demo.model;
import jakarta.persistence.*;

@Entity //indicates that the class is a persistent Java class.
@Table(name = "clothing") //provides the table that maps this entity
public class Item{
    @Id // for the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO) // generates ID automatically
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;

    @Column(name = "price")
    private float price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    public Item(){
    }

    public Item(String title, String description, Size size, float price, Category category){
        this.title = title;
        this.description = description;
        this.size = size;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size){
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public java.lang.String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", category=" + category.getName().name() +
                '}';
    }
}
