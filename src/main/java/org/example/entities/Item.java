package org.example.entities;

import java.math.BigDecimal;

/**
 * Predstavlja medicinski artikl ili uslugu.
 * <p>Sadrži ID, naziv i cijenu artikla.</p>
 */


public class Item implements Trackable {
    private Long id;
    private String name;
    private BigDecimal price;

    public Item(Long id, String name, BigDecimal price) {
        this.id = id; this.name = name; this.price = price;
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public BigDecimal getPrice(){ return price; }

    @Override
    public String trackingId() { return "ITEM - " + id; }

    @Override
    public String toString(){
        return "Item{" + id + ", " + name + ", " + price + "}";
    }
}
