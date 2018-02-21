package nl.avans.ivh11.DemoApplication.repository;

import nl.avans.ivh11.DemoApplication.domain.OrderOption;
import org.springframework.data.repository.CrudRepository;

public interface OrderOptionRepository extends CrudRepository<OrderOption, Long> {
}
