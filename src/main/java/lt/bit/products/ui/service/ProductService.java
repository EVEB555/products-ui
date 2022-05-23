package lt.bit.products.ui.service;

import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.error.ProductValidator;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {


    private List<Product> products = new ArrayList<>();

    public ProductService() {

       products.add(new Product("Product1", BigDecimal.valueOf(10.50), 5, "Red apple"));
       products.add(new Product("Product2", BigDecimal.valueOf(12.35), 11, "Blue Umbrella"));
       products.add(new Product("Product3", BigDecimal.valueOf(9.07), 27, "Bananna"));
       products.add(new Product("Product4", BigDecimal.valueOf(3.99), 55, "Smartphone"));
       products.add(new Product("Product5", BigDecimal.valueOf(59.78), 3, "Juice"));
    }

    public List<Product> getProducts () {
        return products;
    }//metodas grazina produktu sarasa


    public void saveProduct (Product product) {
       // products.forEach(p->p.getId().equals(product.getId())); // lygina su produktu kuris ateis kaip parametras
        Product existingProduct = findProduct(product.getId());
        if (existingProduct == null) {
            products.add(product);
        } else {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setDescription(product.getDescription());
        }
    }


    public void deleteProduct(UUID id) {
        products.remove((findProduct(id)));
    }

    public Product getProduct(UUID id) {
        return findProduct(id);
    }

    private Product findProduct(UUID id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
