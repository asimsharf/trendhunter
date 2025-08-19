package com.sudagoarth.trendhunter.controller;

import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.service.SimilarityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
  private final SimilarityService similarity;

  @GetMapping("/similar")
  public List<Product> similar(@RequestParam("q") String q,
                               @RequestParam(defaultValue = "10") int limit) {
    return similarity.similarToText(q, limit);
  }
}
