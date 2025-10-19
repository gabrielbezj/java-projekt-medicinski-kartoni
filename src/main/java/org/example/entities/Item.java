package org.example.entities;

public class Item {
    private Long id;
    private String name;
    private Double price;

    public Item(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
