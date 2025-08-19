package com.sudagoarth.trendhunter.controller;

import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.service.SimilarityService;
import com.sudagoarth.trendhunter.service.TrendingService;
import com.sudagoarth.trendhunter.service.dto.SimilarProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SimilarityService similarity;


    private final TrendingService trending;

    // Semantic search (lightweight DTO)
    @GetMapping("/search")
    public List<SimilarProductDto> search(@RequestParam("q") String q, @RequestParam(value = "k", defaultValue = "10") int k) {
        return similarity.searchByText(q, k);
    }

    // Return full Product entities if you need more fields
    @GetMapping("/similar")
    public List<Product> similar(@RequestParam("q") String q, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return similarity.similarToText(q, limit);
    }

    // Trending (newest + cheapest in stock)
    @GetMapping("/trending")
    public List<Map<String, Object>> trending(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return trending.trending(category, limit);
    }
}
