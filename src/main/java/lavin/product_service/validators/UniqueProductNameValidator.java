package lavin.product_service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lavin.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        // Verifica se o nome não é nulo e se ele não existe no banco de dados
        return name != null && !productRepository.existsByName(name);
    }
}
