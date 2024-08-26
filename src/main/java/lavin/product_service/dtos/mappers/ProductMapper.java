package lavin.product_service.dtos.mappers;

import lavin.product_service.dtos.request.ProductRequest;
import lavin.product_service.dtos.response.ProductResponse;
import lavin.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse mapProductToResponse(Product product);
    Product mapRequestToProduct(ProductRequest request);
}
