package com.cat.shopapp.services;

import com.cat.shopapp.dtos.ProductDTO;
import com.cat.shopapp.dtos.ProductImageDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.Product;
import com.cat.shopapp.models.ProductImage;
import com.cat.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws DataNotFoundException;
    Page<ProductResponse> getAllProduct(PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(long productId, ProductImageDTO productImageDTO) throws Exception;
}
