package org.brushwood.myeii.mycamel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("orderItems")
    private List<OrderItem> orderItems = new ArrayList<>();

    private int number;

    public Order() {}
    @JsonCreator
    public Order(@JsonProperty("number") int number) {
        this.number = number;
    }

    public void addItem(DrinkType drinkType, int shots, boolean iced) {
        this.orderItems.add(new OrderItem(this, drinkType, shots, iced));
    }

    @JsonProperty("number")
    public int getNumber() {
        return number;
    }

    @JsonProperty("orderItems")
    public List<OrderItem> getItems() {
        return this.orderItems;
    }
}
