package com.sudagoarth.trendhunter.controller;

import com.sudagoarth.trendhunter.service.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrendingController {

  private final TrendingService trendingService;

  @GetMapping("/trending")
  public List<Map<String,Object>> trending(
      @RequestParam(required = false) String category,
      @RequestParam(defaultValue = "20") int limit
  ) {
    return trendingService.trending(category, limit);
  }
}
