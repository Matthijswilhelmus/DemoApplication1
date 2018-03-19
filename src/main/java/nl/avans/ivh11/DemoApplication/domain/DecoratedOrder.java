package nl.avans.ivh11.DemoApplication.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public abstract class DecoratedOrder extends BaseOrder {

    @OneToOne
    protected BaseOrder baseOrder;

    protected DecoratedOrder(BaseOrder baseOrder){this.baseOrder = baseOrder;}
}
