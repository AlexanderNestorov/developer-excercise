package com.example.cloudruid.controller;

import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.request.product.ProductAddRequest;
import com.example.cloudruid.model.response.MessageResponse;
import com.example.cloudruid.model.service.product.ProductAddServiceModel;
import com.example.cloudruid.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ModelMapper modelMapper;
    private final ProductService productService;

    public ProductController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        Set<Product> products = this.productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductAddRequest productAddRequest,
                                        BindingResult bindingResult) {


        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid product request data!"));
        }

        if (productService.existsByName(productAddRequest.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Product with this name already exists!"));
        }

        ProductAddServiceModel productAddServiceModel =
                modelMapper.map(productAddRequest, ProductAddServiceModel.class);


        productService.addProduct(productAddServiceModel);


        return ResponseEntity.ok(new MessageResponse("Product added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {

        Long deletionId = productService.getProductById(id).getId();

        if(deletionId == null){
            return ResponseEntity.notFound().build();
        }

        this.productService.deleteProduct(id);

        return ResponseEntity.ok(new MessageResponse("Product deleted successfully!"));
    }
}
