package com.krishna.productService_sst.services;

import com.krishna.productService_sst.dtos.FakeStoreProductDto;
import com.krishna.productService_sst.models.Category;
import com.krishna.productService_sst.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] productDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        if (productDtos != null) {
            List<Product> products = new ArrayList<>();
            for (FakeStoreProductDto dto : productDtos) {
                Product product = new Product();
                product.setId(dto.getId());
                product.setTitle(dto.getTitle());
                product.setPrice(dto.getPrice());
                product.setImageUrl(dto.getImage());
                product.setDescription(dto.getDescription());
                Category category = new Category();
                category.setName(dto.getCategory());
                product.setCategory(category);
                products.add(product);
            }
            return products;
        }
        return null;
    }

    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDto fakeStoreProductDto =  restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());

        return product;
    }

    @Override
    public Product createProduct(Product product) {
        //update product to fakestoreapi
        //https://fakestoreapi.com/products/id

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreProductDto,
                FakeStoreProductDto.class
        );
        return null;
    }

    @Override
    public Product updateProduct(Product product) {

        //update product to fakestoreapi

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        restTemplate.put(
                "https://fakestoreapi.com/products/" + product.getId(),
                fakeStoreProductDto
        );

        return product;

    }

    @Override
    public void deleteProduct(Long id) {

        restTemplate.delete("https://fakestoreapi.com/products/" + id);

    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {

        //'https://fakestoreapi.com/products/category/jewelery'
        FakeStoreProductDto[] productDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + categoryName,
                FakeStoreProductDto[].class
        );

        if (productDtos != null) {
            List<Product> products = new ArrayList<>();
            for (FakeStoreProductDto dto : productDtos) {
                Product product = new Product();
                product.setId(dto.getId());
                product.setTitle(dto.getTitle());
                product.setPrice(dto.getPrice());
                product.setImageUrl(dto.getImage());
                product.setDescription(dto.getDescription());
                Category category = new Category();
                category.setName(dto.getCategory());
                product.setCategory(category);
                products.add(product);
            }
            return products;
        }

        return null;


    }

    @Override
    public List<String> getCategories() {
        //https://fakestoreapi.com/products/categories
        String[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        if (categories != null) {
            List<String> categoryList = new ArrayList<>();
            for (String category : categories) {
                categoryList.add(category);
            }
            return categoryList;
        }
        return null;

    }


}
