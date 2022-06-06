package lt.bit.products.ui.service;

import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.domain.ProductEntity;
import lt.bit.products.ui.service.domain.ProductRepository;
import lt.bit.products.ui.service.error.ErrorCode;
import lt.bit.products.ui.service.error.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.springframework.util.StringUtils.hasLength;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;
    private List<Product> products = new ArrayList<>();

    public ProductService(ProductRepository repository) {
/*
       products.add(new Product("Product1", BigDecimal.valueOf(10.50), 5, "Red apple"));
       products.add(new Product("Product2", BigDecimal.valueOf(12.35), 11, "Blue Umbrella"));
       products.add(new Product("Product3", BigDecimal.valueOf(9.07), 27, "Bananna"));
       products.add(new Product("Product4", BigDecimal.valueOf(3.99), 55, "Smartphone"));
       products.add(new Product("Product5", BigDecimal.valueOf(59.78), 3, "Juice"));*/
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    public List<Product> getProducts() {
        List<ProductEntity> products = repository.findAll();
        // @formatter:off
        return mapper.map(products, new TypeToken<List<Product>>() {}.getType());
        // @formatter:on
    }//metodas grazina produktu sarasa


    public void saveProduct(Product product) throws ValidationException {
        UUID id = product.getId();
        if (id != null && !repository.existsById(id)) {
            throw new ValidationException(ErrorCode.PRODUCT_NOT_FOUND, id);
        }
        repository.save(mapper.map(product, ProductEntity.class));
    }

    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }

    public Product getProduct(UUID id) {
        return findProduct(id);
    }

    private Product findProduct(UUID id) {
        return repository.findById(id).map(p -> mapper.map(p, Product.class)).orElseThrow();
    }

    public List<Product> findProducts(String id, String name) {
        List<ProductEntity> products = new ArrayList<>();
        if (hasLength(id) && hasLength(name)) {
            products.addAll(repository.findByNameContainingOrIdIs(name, UUID.fromString(id)));
        } else if (hasLength(name)) {
            products.addAll(repository.findByNameContaining(name));
        } else {
            products.add(repository.findById(UUID.fromString(id)).orElseThrow());
        }
        // @formatter:off
        return mapper.map(products, new TypeToken<List<Product>>() {}.getType());
        // @formatter:on
    }

    public List<Product> findProductsWithQuery(String id, String name) {
        List<ProductEntity> products = repository.findByNameAndIdOptional(defaultIfEmpty(name, null),
                hasLength(id) ? UUID.fromString(id) : null);
        // @formatter:off
        return mapper.map(products, new TypeToken<List<Product>>() {}.getType());
        // @formatter:on
    }
}
