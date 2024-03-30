package org.brushwood.myeii.mycamel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.camel.util.ObjectHelper;
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "drink_type")
    private DrinkType type;
    private int shots = 1;
    private boolean iced;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    public OrderItem() {}
    @JsonCreator
    public OrderItem(@JsonProperty("order") Order order,
                     @JsonProperty("type") DrinkType type,
                     @JsonProperty("shots") int shots,
                     @JsonProperty("iced") boolean iced) {
        this.order = order;
        this.type = type;
        this.shots = shots;
        this.iced = iced;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
