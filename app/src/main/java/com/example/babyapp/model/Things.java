package com.example.babyapp.model;

public class Things {

    public int id;
    public String name;
    public String color;
    public String size;
    public String quantity;
    public String date;

    public Things(int id, String name, String color, String size, String quantity, String date) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.date = date;
    }

    public Things() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
