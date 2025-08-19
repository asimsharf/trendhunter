package com.sudagoarth.trendhunter.service;


import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final SimilarityService similarityService;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product save(Product p) {
        Product saved = productRepository.save(p);
        // async indexing would be nicer; for now do it inline
        similarityService.indexProduct(saved);
        return saved;
    }

}