package lt.bit.products.ui.service.error;

import lt.bit.products.ui.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Component
public class ProductValidator {

    public void validate(Product product) throws ValidationException {
        validateName(product.getName());
        validateQuantity(product.getQuantity());
        //validateDescription(product.getDescription());
    }

    private void validateName(String name) throws ValidationException {
        if (!StringUtils.hasLength(name)) {
            throw new ValidationException(ErrorCode.EMPTY_VALUE, new Object[]{"Name"});
        }

        if (name.length() < 3) {
            throw new ValidationException(ErrorCode.VALUE_TOO_SHORT, new Object[]{"Name", 3});
        }
        if (name.length() > 21) {
            throw new ValidationException(ErrorCode.VALUE_TOO_LONG, new Object[]{"Name", 20});
        }
    }

    private void validateQuantity(double quantity) throws ValidationException {
        if (quantity < 0) {
            throw new ValidationException(ErrorCode.NEGATIVE_NUMBER, new Object[]{"Quantity"});
        }
    }

   /* private void validateDescription(String description) throws ValidationException {
        Integer[] integers = new Integer[description.length()];
        for (int i = 0; i < 10; ++i) {
            int index = description.indexOf(i + "");
            while (index != -1) {
                integers[index] = i;
                index = description.indexOf(i + "",index + 1);
            }
        }
        throw new ValidationException(ErrorCode.VALUE_CONTAINS_DIGITS, new Object[]{"Description",
                Arrays.toString(integers)});
    }*/
}