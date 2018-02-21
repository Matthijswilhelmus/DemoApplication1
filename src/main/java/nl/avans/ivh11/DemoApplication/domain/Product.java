package nl.avans.ivh11.DemoApplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Message may not be empty.")
    private String name;

    @NotEmpty(message = "Message is required.")
    private String description;

    @NotNull(message = "Price is required.")
    @Min(0) // at least 0
    private int price = 0;

    public Product() {}

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Product p) {
        this.name = p.name;
        this.price = p.price;
        this.description = p.description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
