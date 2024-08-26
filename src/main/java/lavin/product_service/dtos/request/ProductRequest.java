package lavin.product_service.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lavin.product_service.validators.UniqueProductName;

import java.math.BigDecimal;

public record ProductRequest(
        @UniqueProductName
        @NotEmpty(message = "Name is required")
        String name,

        @NotEmpty(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        Integer stock
) {}

