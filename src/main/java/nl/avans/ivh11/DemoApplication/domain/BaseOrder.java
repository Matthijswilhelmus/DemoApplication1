package nl.avans.ivh11.DemoApplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseOrder {

    @Id
    @GeneratedValue
    private Long id;

    public abstract int price();

}
