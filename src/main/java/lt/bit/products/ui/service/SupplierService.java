package lt.bit.products.ui.service;


import lt.bit.products.ui.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    List<Supplier> getSuppliers();

    Supplier getSupplier(UUID id);

    void saveSupplier(Supplier supplier);
}