package nl.avans.ivh11.DemoApplication.repository;

import nl.avans.ivh11.DemoApplication.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
