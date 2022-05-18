package lt.bit.products.ui.controller;

import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController { //controller klases atsakingos uz adreso mapping
    private ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    String showProducts (Model model) {
        List<Product> products = service.getProducts();
        model.addAttribute("productItems", products); //view'e esantis kintamasis taip vadinsis
        return "productList"; //view pavadinimas
    }

    @GetMapping("/products/{id}") //get visi imputai, text ir pns siunciami i url (adresa), daznai naudojama paieskoms
    String editProducts (@PathVariable UUID id, Model model) {
        model.addAttribute("productItem", service.getProduct(id));
        return "productForm";
    }

    @GetMapping("/products/add")
    String addProducts (Model model) {
        model.addAttribute("productItem", new Product());
        return "productForm";
    }

    @PostMapping("/products/save") //post metodas siuncia info per request body (nematoma dali), formoms dazniausiai naudojamas post
    String saveProduct(@ModelAttribute Product product, Model model) { // model atributas padeda model nukeliauti i view
       String error = hasError(product.getName());
        if (error != null) {
            model.addAttribute("errorMsg", error); //model reikalingas kad prideti attributes
            model.addAttribute("productItem", product);
            return "productForm";
       }
        service.saveProduct(product);
        return "redirect:/products";
    }
    private String hasError(String name) {
        if (!StringUtils.hasLength(name)) {
        return "Name is required";
        }
        if (name.length() < 3) {
            return "Name is too short";
        }
        return null;
    }

    @GetMapping("/products/delete")
    String deleteProducts (@RequestParam UUID id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }

}
