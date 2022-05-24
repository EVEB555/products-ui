package lt.bit.products.ui.controller;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.error.ProductValidator;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ProductController {

    private final ProductService service;
    private final UserService userService;
    private final ProductValidator validator;
    private final MessageSource messages;

    ProductController(ProductService service, UserService userService, ProductValidator validator, //controller klases atsakingos uz adreso mapping
                      MessageSource messages) {
        this.service = service;
        this.userService = userService;
        this.validator = validator;
        this.messages = messages;
    }

    @GetMapping("/products")
    String showProducts(Model model) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        List<Product> products = service.getProducts();
        model.addAttribute("productItems", products);
        return "productList";
    }

    @GetMapping("/products/{id}") //get visi imputai, text ir pns siunciami i url (adresa), daznai naudojama paieskoms
    String editProduct(@PathVariable UUID id, Model model) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        model.addAttribute("productItem", service.getProduct(id)); //view'e esantis kintamasis taip vadinsis
        return "productForm";
    }

    @GetMapping("/products/add")
    String addProduct(Model model) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        model.addAttribute("productItem", new Product());
        return "productForm";
    }

    @PostMapping("/products/save") //post metodas siuncia info per request body (nematoma dali), formoms dazniausiai naudojamas post
    String saveProduct(@ModelAttribute Product product, Model model) {  // model atributas padeda model nukeliauti i view
        try {
            validator.validate(product);
        } catch (ValidationException e) {
            model.addAttribute("errorMsg",
                    messages.getMessage("validation.error." + e.getCode(), e.getParams(),
                            Locale.getDefault()));
            model.addAttribute("productItem", product); //model reikalingas kad prideti attributes
            return "productForm";
        }
        service.saveProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/products/delete")
    String deleteProduct(@RequestParam UUID id) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        service.deleteProduct(id);
        return "redirect:/products";
    }
}