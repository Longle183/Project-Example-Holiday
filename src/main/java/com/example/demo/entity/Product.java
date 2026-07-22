package com.example.demo.entity;

public class Product implements java.io.Serializable {
    public long id; public String name, category, description, imageUrl; public double price; public int stock; public boolean active;
    public Product(long id, String name, String category, double price, int stock, boolean active, String description, String imageUrl) { this.id=id; this.name=name; this.category=category; this.price=price; this.stock=stock; this.active=active; this.description=description; this.imageUrl=imageUrl; }
    public long getId(){return id;} public String getName(){return name;} public String getCategory(){return category;} public String getDescription(){return description;} public String getImageUrl(){return imageUrl;} public double getPrice(){return price;} public int getStock(){return stock;} public boolean isActive(){return active;}
}
