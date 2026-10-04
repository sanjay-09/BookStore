package com.flipkart.catalog_service.Controller;

import com.flipkart.catalog_service.Dto.PagedResult;
import com.flipkart.catalog_service.Dto.ProductResDto;
import com.flipkart.catalog_service.Model.Product;
import com.flipkart.catalog_service.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(name="page",defaultValue ="1")int pageNo){

        PagedResult<ProductResDto> res=this.productService.getProductByPage(pageNo);
        return ResponseEntity.status(HttpStatus.OK).body(res);

    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getProductByCode(@PathVariable  String code){

        log.info("Fetching prodcut for code:{}",code);
        ProductResDto productResDto=this.productService.getProductByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(productResDto);

    }


}
