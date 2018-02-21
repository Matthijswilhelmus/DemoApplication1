package nl.avans.ivh11.DemoApplication.domain;


import com.sun.xml.internal.org.jvnet.staxex.Base64EncoderStream;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public abstract class DecoratedOrder extends BaseOrder {

    @OneToOne
    protected BaseOrder baseOrder;

    protected DecoratedOrder(BaseOrder baseOrder){this.baseOrder = baseOrder;}
    //public DecoratedOrder(BaseOrder order) {
    //}

    //public abstract void DecoratedOrder();
}
