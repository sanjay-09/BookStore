package com.flipkart.catalog_service.Repository;


import com.flipkart.catalog_service.Exception.ResourceNotFoundException;
import com.flipkart.catalog_service.Model.Product;
import com.flipkart.catalog_service.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
//                "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
        }
)
@Import(TestcontainersConfiguration.class)
@Sql("/test-data.sql")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void showGetAllProducts(){
        List<Product> products=this.productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void getProductByCode(){
        Product product=this.productRepository.findByCode("P102").orElseThrow();
        assertThat(product).isNotNull();
        assertThat(product.getCode()).isEqualTo("P102");
    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists(){
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
    }

}
