package lavin.product_service.service;

import lavin.product_service.dtos.mappers.ProductMapper;
import lavin.product_service.dtos.request.ProductRequest;
import lavin.product_service.dtos.request.ProductRequestStock;
import lavin.product_service.dtos.response.ProductResponse;
import lavin.product_service.exceptions.ExceptionProductCannotBeDeleted;
import lavin.product_service.exceptions.ExceptionProductNotFound;
import lavin.product_service.model.Product;
import lavin.product_service.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<ProductResponse> listProductById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ExceptionProductNotFound();
        }
        ProductResponse productResponse = productMapper.mapProductToResponse(productOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    public ResponseEntity<ProductResponse> updateProductByIdAndDeactivate(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ExceptionProductNotFound();
        }
        Product product = productOptional.get();
        if (product.getStock() == 0) {
            throw new ExceptionProductCannotBeDeleted();
        }
        product.setActive(false);
        var productSave = productRepository.save(product);
        ProductResponse productResponse = productMapper.mapProductToResponse(productSave);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    public ResponseEntity<ProductResponse> updateProductStock(String id, ProductRequestStock productRequestStock) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ExceptionProductNotFound();
        }
        var quantityUpdated = productRequestStock.stock() + productOptional.get().getStock();
        productOptional.get().setStock(quantityUpdated);
        productOptional.get().setActive(true);
        var saveProductChanged = productRepository.save(productOptional.get());
        ProductResponse productResponse = productMapper.mapProductToResponse(saveProductChanged);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }
}
