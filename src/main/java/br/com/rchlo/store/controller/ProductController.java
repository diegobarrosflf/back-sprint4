package br.com.rchlo.store.controller;

import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.dto.ProductDto;
import br.com.rchlo.store.projection.ProductByColorProjection;
import br.com.rchlo.store.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Cacheable(value = "productList")
    public List<ProductDto> productsByColorReport() {
        return productRepository.findAllWithImagesCategoryAndSizesOrderByName().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @GetMapping("/reports/products/by-color")
    public List<ProductByColorProjection> productByColorReport() {
        return productRepository.productsByColor();
    }

    @GetMapping("/products/clear-cache")
    @CacheEvict(value = "productList", allEntries = true)
    public void cleanCache() {
        System.out.println("Limpando cache.");
    }


}
