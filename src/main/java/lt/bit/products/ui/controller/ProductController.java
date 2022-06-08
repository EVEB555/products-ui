package lt.bit.products.ui.controller;

import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.SupplierService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.error.ProductValidator;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasLength;

@Controller
class ProductController {

    private final ProductService service;
    private final UserService userService;
    private final ProductValidator validator;
    private final MessageSource messages;
    private final SupplierService supplierService;

    ProductController(ProductService service, UserService userService, ProductValidator validator, //controller klases atsakingos uz adreso mapping
                      MessageSource messages, SupplierService supplierService) {
        this.service = service;
        this.userService = userService;
        this.validator = validator;
        this.messages = messages;
        this.supplierService = supplierService;
    }

    @GetMapping("/products")
    String showProducts(Model model, HttpServletRequest request) {
        if (!userService.isAuthenticated()) {
            return "login";
        }

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        List<Product> products;
        if (hasLength(id) || hasLength(name)) {
            products = service.findProducts(id, name);
//      products = service.findProductsWithQuery(id, name);
        } else {
            products = service.getProducts();
        }

        model.addAttribute("searchCriteriaId", id);
        model.addAttribute("searchCriteriaName", name);
        model.addAttribute("productItems", products);
        return "productList";
    }

    @GetMapping("/products/{id}") //get visi imputai, text ir pns siunciami i url (adresa), daznai naudojama paieskoms
    String editProduct(@PathVariable UUID id, Model model) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        model.addAttribute("productItem", service.getProduct(id)); //view'e esantis kintamasis taip vadinsis
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "productForm";
    }

    @GetMapping("/products/add")
    String addProduct(Model model) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        model.addAttribute("productItem", new Product());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "productForm";
    }

    @PostMapping("/products/save") //post metodas siuncia info per request body (nematoma dali), formoms dazniausiai naudojamas post
    String saveProduct(@ModelAttribute Product product, @RequestParam(name = "imageFile", required = false) MultipartFile file, Model model) throws ValidationException {  // model atributas padeda model nukeliauti i view; required false - neprivalomas failas
        try {
            validator.validate(product);
            if(file != null & !file.isEmpty()) {
                validator.validate(file);
                product.setImageName(file.getOriginalFilename());
                product.setImageFileContents(file.getBytes());
            }
        } catch (ValidationException e) {
            model.addAttribute("errorMsg",
                    messages.getMessage("validation.error." + e.getCode(), e.getParams(),
                            Locale.getDefault()));
            model.addAttribute("productItem", product); //model reikalingas kad prideti attributes
            return "productForm";
        } catch (IOException e) {
            model.addAttribute("errorMsg",
                    messages.getMessage("system.error.FILE_UPLOAD", null, Locale.getDefault()));
            model.addAttribute("productItem", product);
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