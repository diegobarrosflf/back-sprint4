package br.com.rchlo.store.repository;

import br.com.rchlo.store.domain.Color;
import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.projection.ProductByColorProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldListAllProductsOrderedByName() {

        Pageable page = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        List<Product> products = productRepository.findAllWithImagesCategoryAndSizesOrderByName(page);

        assertEquals(5, products.size());

        List<Product> productsSortByName = new ArrayList<>(products);
        productsSortByName.sort(Comparator.comparing(Product::getName));

        Product firstProduct = products.get(0);
        Product firstProductExpected = productsSortByName.get(0);
        assertEquals(firstProductExpected.getCode(), firstProduct.getCode());
        assertEquals(firstProductExpected.getName(), firstProduct.getName());

        Product secondProduct = products.get(1);
        Product secondProductExpected = productsSortByName.get(1);
        assertEquals(secondProductExpected.getCode(), secondProduct.getCode());
        assertEquals(secondProductExpected.getName(), secondProduct.getName());
    }

    @Test
    void shouldRetrieveProductsByColor() {

        List<ProductByColorProjection> productsByColor = productRepository.productsByColor();
        Collections.sort(productsByColor, Comparator.comparing(ProductByColorProjection::getColor));

        assertEquals(3, productsByColor.size());

        ProductByColorProjection firstProductByColorProjection = productsByColor.get(0);
        assertEquals(Color.BLUE.getDescription(), firstProductByColorProjection.getColorDescription());
        assertEquals(1, firstProductByColorProjection.getAmount());

        ProductByColorProjection SecondProductByColorProjection = productsByColor.get(1);
        assertEquals(Color.RED.getDescription(), SecondProductByColorProjection.getColorDescription());
        assertEquals(1, SecondProductByColorProjection.getAmount());

        ProductByColorProjection thirdProductByColorProjection = productsByColor.get(2);
        assertEquals(Color.WHITE.getDescription(), thirdProductByColorProjection.getColorDescription());
        assertEquals(3, thirdProductByColorProjection.getAmount());

    }

}