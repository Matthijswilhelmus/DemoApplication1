package nl.avans.ivh11.DemoApplication.service;


import nl.avans.ivh11.DemoApplication.domain.Order;
import nl.avans.ivh11.DemoApplication.domain.OrderOption;
import nl.avans.ivh11.DemoApplication.domain.Product;
import nl.avans.ivh11.DemoApplication.domain.ProductCatalog;
import nl.avans.ivh11.DemoApplication.logging.MyExecutionTime;
import nl.avans.ivh11.DemoApplication.repository.OrderOptionRepository;
import nl.avans.ivh11.DemoApplication.repository.OrderRepository;
import nl.avans.ivh11.DemoApplication.repository.ProductCatalogRepository;
import nl.avans.ivh11.DemoApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductTransactionService {

    @Autowired
    private final ProductCatalogRepository productCatalogRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderOptionRepository orderOptionRepository;

    public ProductTransactionService(ProductCatalogRepository productCatalogRepository,
                                     OrderRepository orderRepository,
                                     ProductRepository productRepository, OrderOptionRepository orderOptionRepository)
    {
        this.productCatalogRepository = productCatalogRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderOptionRepository = orderOptionRepository;
    }

    /**
     *
     *
     */
    @MyExecutionTime
    @Transactional(propagation = Propagation.NESTED)
    public void createOrder() {

        // get the productCatalog
        Optional<ProductCatalog> productCatalog = Optional.ofNullable(productCatalogRepository.findOne(1L));

        // "find" a product in the catalog and add it to the order
        Product prod = productCatalog.get().decrementStock(2L);

        // make a copy of the product (the copy has no id yet)
        // why a copy is made?
        Product prodCopy = new Product(prod);

        Order order = new Order();
        order = orderRepository.save(order);
        order.add(prodCopy);

        //System.exit(0);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createProductCatalogAndProducts() {

        // build product catalog and two products
        ProductCatalog productCatalog = new ProductCatalog();

        // right productCatalog: without id; left productCatalog: with id
        // (needed because of autoincrement)
        productCatalog = productCatalogRepository.save(productCatalog);

        System.out.println("#product in product catalog: " +
                productCatalog.getProducts().size());

        Product prod1 = new Product("Schroefje", "Zakje schroefjes", 2);
        Product prod2 = new Product("Moertje", "Beschrijving van een moertje", 1);

        // a product must have an id to be stored in product catalog, so save
        // explicitly
        prod1 = productRepository.save(prod1);
        prod2 = productRepository.save(prod2);

        // add two products
        productCatalog.add(prod1, 1);
        productCatalog.add(prod2, 3);
        try {
            Thread.sleep(10000);
        } catch(InterruptedException e) {}

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void decorateOrder() {
        Optional<Order> Order  = Optional.ofNullable(orderRepository.findOne(1L));  //.findById(4L);
        OrderOption decoratedOrder1 = new OrderOption("wrapping paper", 7, Order.get());
        orderOptionRepository.save(decoratedOrder1);
        OrderOption decoratedOrder2 = new OrderOption("nice box", 5, decoratedOrder1);
        orderOptionRepository.save(decoratedOrder2);
        OrderOption decoratedOrder3 = new OrderOption("fast delivery", 12, decoratedOrder2);
        orderOptionRepository.save(decoratedOrder3);
        System.out.println("***** content of the order: " + decoratedOrder3);
        System.out.println("***** price of the order: " + decoratedOrder3.price());
    }
}
