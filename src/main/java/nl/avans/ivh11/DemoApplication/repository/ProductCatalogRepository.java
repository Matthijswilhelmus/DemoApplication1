package nl.avans.ivh11.DemoApplication.repository;

import nl.avans.ivh11.DemoApplication.domain.Product;
import nl.avans.ivh11.DemoApplication.domain.ProductCatalog;
import org.springframework.data.repository.CrudRepository;

public interface ProductCatalogRepository extends CrudRepository<ProductCatalog, Long> {
}
