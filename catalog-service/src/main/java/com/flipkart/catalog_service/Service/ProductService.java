package com.flipkart.catalog_service.Service;

import com.flipkart.catalog_service.Configuration.ApplicationConfiguration;
import com.flipkart.catalog_service.Dto.PagedResult;
import com.flipkart.catalog_service.Dto.ProductResDto;
import com.flipkart.catalog_service.Exception.ResourceNotFoundException;
import com.flipkart.catalog_service.Mapper.ProductToDto;
import com.flipkart.catalog_service.Model.Product;
import com.flipkart.catalog_service.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationConfiguration applicationConfiguration;


    public PagedResult<ProductResDto> getProductByPage(int pageNo){
        int page=pageNo<=1?0:pageNo-1;
        Pageable pageable= PageRequest.of(page,applicationConfiguration.getPageNo(), Sort.by("name").ascending());
        Page<ProductResDto> products=this.productRepository.findAll(pageable).map(product -> ProductToDto.toDto(product));



        PagedResult<ProductResDto> productList=new PagedResult<>(products.getContent(),
                products.getTotalElements(),
                products.getNumber()+1,
                products.getTotalPages(),
                products.isFirst(),
                products.isLast(),
                products.hasNext(),
                products.hasPrevious()
                );

        return productList;




    }

    public ProductResDto getProductByCode(String code){
        System.out.println("code"+ code);
        Product product=this.productRepository.findByCode(code).orElseThrow(()->new ResourceNotFoundException("product with code: "+code+" does not exist"));
        return ProductToDto.toDto(product);

    }





}
