package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductDto;
import br.com.rchlo.store.projection.ProductByColorProjection;
import br.com.rchlo.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {


    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Cacheable(value = "productList")
    public List<ProductDto> productsByColorReport(
            @PageableDefault(sort="name", direction = Sort.Direction.ASC, page=0, size=3) Pageable pageable) {

                List<Product> products = productRepository.findAllWithImagesCategoryAndSizesOrderByName(pageable);

        return ProductDto.convert(products);

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
