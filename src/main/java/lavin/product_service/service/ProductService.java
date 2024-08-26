package lavin.product_service.service;

import lavin.product_service.dtos.mappers.ProductMapper;
import lavin.product_service.dtos.request.ProductRequest;
import lavin.product_service.dtos.response.ProductResponse;
import lavin.product_service.model.Product;
import lavin.product_service.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<List<ProductResponse>> listAllProducts(Boolean showInactive) {
        List<Product> productList = productRepository.findAll();
        if (!showInactive) {
            productList = productList.stream()
                    .filter(Product::getActive)
                    .toList();
        }
        List<ProductResponse> productResponseList = productList.stream().map(productMapper::mapProductToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseList);
    }

    public ResponseEntity<ProductResponse> addProduct(ProductRequest productRequest) {
        Product product = productMapper.mapRequestToProduct(productRequest);
        Product productSaved = productRepository.save(product);
        ProductResponse productResponse = productMapper.mapProductToResponse(productSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }
}
