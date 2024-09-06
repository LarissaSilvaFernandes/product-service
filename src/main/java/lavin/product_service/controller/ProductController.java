package lavin.product_service.controller;

import jakarta.validation.Valid;
import lavin.product_service.dtos.request.ProductRequest;
import lavin.product_service.dtos.request.ProductRequestStock;
import lavin.product_service.dtos.response.ProductResponse;
import lavin.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> listProducts(@RequestParam(defaultValue = "false") boolean showInactive) {
        return productService.listAllProducts(showInactive);
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        return productService.listProductById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductDeactivate(@PathVariable String id) {
        return productService.updateProductByIdAndDeactivate(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductStock(@PathVariable String id, @RequestBody @Valid ProductRequestStock productRequestStock) {
        return productService.updateProductStock(id, productRequestStock);
    }
}
