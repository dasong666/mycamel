package org.brushwood.myeii.mycamel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.camel.util.ObjectHelper;
@Entity
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean iced;
    private int shots;
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;
    private int orderNumber;

    public Drink() {}
    @JsonCreator
    public Drink(@JsonProperty("orderNumber") int orderNumber,
                 @JsonProperty("drinkType") DrinkType drinkType,
                 @JsonProperty("iced") boolean iced,
                 @JsonProperty("shots") int shots) {
        this.orderNumber = orderNumber;
        this.drinkType = drinkType;
        this.iced = iced;
        this.shots = shots;
    }

    // Other methods...
}
