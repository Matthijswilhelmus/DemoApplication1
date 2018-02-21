package nl.avans.ivh11.DemoApplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

//@NoArgsConstructor
@Entity
@Getter
@Setter
public class OrderOption extends DecoratedOrder {

    private String name;
    private int price;

    public OrderOption(String name, int price, BaseOrder order) {
        super(order);
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderOption" + name + "," + baseOrder;
    }


    @Override
    public int price() {
        return this.price + baseOrder.price();
    }

}
