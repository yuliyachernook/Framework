package model;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Item {
    private String name;
    private String size;
    private String color;
    private double price;
    private int count;

    private Item(String name, String size, String color, double cost, int count) {

        this.name = name;
        this.size = size;
        this.color = color;
        this.price = cost;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() { return size;}

    public void setSize(String size) { this.size = size;}

    public String getColor() { return color;}

    public void setColor(String color) { this.size = color;}

    public double getPrice() { return price;}

    public void setPrice(int price){ this.price = price;}

    public int getCount() { return count;}

    public void setCount(int count) { this.count = count;}
    public static Item of(String name, String size, String color, double cost, int count){
        return new Item(name, size, color, cost, count);
    }

    public void changeAmount(int amount) {
        this.count = amount;
        this.price = price * amount;
    }
}