package nl.avans.ivh11.DemoApplication.controller;

import nl.avans.ivh11.DemoApplication.domain.*;
import nl.avans.ivh11.DemoApplication.repository.OrderOptionRepository;
import nl.avans.ivh11.DemoApplication.repository.OrderRepository;
import nl.avans.ivh11.DemoApplication.repository.ProductCatalogRepository;
import nl.avans.ivh11.DemoApplication.repository.ProductRepository;
import nl.avans.ivh11.DemoApplication.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ArrayList<Product> products = new ArrayList<>();

    // Views constants
    private final String VIEW_LIST_PRODUCTS = "views/product/list";
    private final String VIEW_CREATE_PRODUCT = "views/product/create";
    private final String VIEW_READ_PRODUCT = "views/product/read";

    @Autowired
    private final ProductService productService;
    private ProductCatalogRepository productCatalogRepository;
    private OrderRepository orderRepository;
    private OrderOptionRepository orderOptionRepository;

    // Constructor with Dependency Injection
    public ProductController(
            ProductService service,
            ProductCatalogRepository productCatalogRepository,
            OrderRepository orderRepository
    ) {
        this.productService = service;
        this.productCatalogRepository = productCatalogRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @GetMapping(value = "/addsomemore")
    public ModelAndView addSomeMoreProducts(Model model) {

        logger.debug("addSomeMoreProducts called.");

        // createProductCatalogAndProducts();
        createOrder();

        Iterable<Product> products = productService.getProducts();

        return new ModelAndView("redirect:/products", "products", products);
    }

    @GetMapping
    public String listProducts(
            @RequestParam(value = "category", required = false, defaultValue = "all") String category,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size,
            Model model) {

        logger.debug("listProducts called.");

        Iterable<Product> products = productService.getProducts();

        model.addAttribute("category", category);
        model.addAttribute("size", size);
        model.addAttribute("products", products);
        return VIEW_LIST_PRODUCTS;
    }

    @GetMapping("{id}")
    public ModelAndView viewProduct(@PathVariable("id") Product product)
            throws Exception {
        if (null == product) {
            throw new Exception("Product niet gevonden!");
        }
        return new ModelAndView(VIEW_READ_PRODUCT, "product", product);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showCreateProductForm(final Product product, final ModelMap model) {
        logger.debug("showCreateProductForm");
        return VIEW_CREATE_PRODUCT;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView validateAndSaveProduct(
            @Valid Product product,
            final BindingResult bindingResult,
            RedirectAttributes redirect) {

        logger.debug("validateAndSaveProduct - adding product " + product.getName());
        if (bindingResult.hasErrors()) {
            logger.debug("validateAndSaveProduct - not added, bindingResult.hasErrors");
            return new ModelAndView(VIEW_CREATE_PRODUCT, "formErrors", bindingResult.getAllErrors());
        }

        product = productService.createProduct(product);

        redirect.addFlashAttribute("globalMessage", "Successfully created a new product");
        return new ModelAndView("redirect:/products/{product.id}", "product.id", product.getId());
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") Product product) {
        return new ModelAndView(VIEW_CREATE_PRODUCT, "product", product);
    }
    @Transactional
    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Product product) {

        createOrder();
        decorateOrder();

        return "product/form";
    }



    /**
     *
     *
     */
    public void createProductCatalogAndProducts() {

        // build product catalog and two products
        ProductCatalog productCatalog = new ProductCatalog();

        // right productCatalog: without id; left productCatalog: with id
        // (needed because of autoincrement)
        productCatalog = productCatalogRepository.save(productCatalog);

        Product prod1 = new Product("Schroefje", "Zakje schroefjes", 2);
        Product prod2 = new Product("Moertje", "Beschrijving van een moertje", 1);

        // add two products
        productCatalog.add(prod1, 1);
        productCatalog.add(prod2, 3);
    }
    private void decorateOrder() {
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


    /**
     *
     *
     */
    public void createOrder() {

        // get the productCatalog
        ProductCatalog productCatalog = productCatalogRepository.findOne(1L);

        // "find" a product in the catalog and add it to the order
        Product prod = productCatalog.find(2L);

        // make a copy of the product (the copy has no id yet)
        // why a copy is made?
        Product prodCopy = new Product(prod);

        Order order = new Order();
        order = orderRepository.save(order);
        order.add(prodCopy);
    }
}
