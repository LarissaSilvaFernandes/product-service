package lavin.product_service.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductRequestStock(
        @NotNull(message = "Stock is required")
        @Min(1)
        Integer stock
) {}
