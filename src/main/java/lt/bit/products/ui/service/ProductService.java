package lt.bit.products.ui.service;

import lt.bit.products.ui.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    public ProductService() {
       products.add(new Product("Product1", BigDecimal.valueOf(10.50), 5));
       products.add(new Product("Product2", BigDecimal.valueOf(12.35), 11));
       products.add(new Product("Product3", BigDecimal.valueOf(9.07), 27));
       products.add(new Product("Product4", BigDecimal.valueOf(3.99), 55));
       products.add(new Product("Product5", BigDecimal.valueOf(59.78), 3));
    }

    public List<Product> getProducts () {
        return products;
    }//metodas grazina produktu sarasa

    public void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    public void updateProduct(Product product) {
       // products.forEach(p->p.getId().equals(product.getId())); // lygina su produktu kuris ateis kaip parametras

       Product existingPrroduct = findProduct(product.getId());
       if (existingPrroduct != null) {
           existingPrroduct.setName((product.getName()));
           existingPrroduct.setPrice((product.getPrice()));
           existingPrroduct.setQuantity(product.getQuantity());
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
