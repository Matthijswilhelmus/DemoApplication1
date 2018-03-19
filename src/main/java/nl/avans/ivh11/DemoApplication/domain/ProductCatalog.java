package nl.avans.ivh11.DemoApplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductCatalog {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private Map<Long, StockItem> products = new HashMap<>();

    // add new product to catalog and indicate number of stock items
    // precondition: product must have an id!
    public void add(Product product, int quantity) {
        assert(product.getId() != null);
        products.put(product.getId(), new StockItem(product, quantity));
    }

    // precondition: product in catalog
    // precondition: at least one product in stock
    public Product decrementStock(Long productId) {
        assert(products.containsKey(productId));
        assert(products.get(productId).getQuantity() >= 0);

        StockItem stocki = products.get(productId);
        products.put(productId, stocki.decrementStock());
        return stocki.getProduct();
    }
    /*
    public ProductCatalog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Product> products) {
        this.products = products;
    } */
}
