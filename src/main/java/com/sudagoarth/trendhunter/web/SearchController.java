package com.sudagoarth.trendhunter.web;

import com.sudagoarth.trendhunter.service.SimilarityService;
import com.sudagoarth.trendhunter.service.dto.SimilarProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("searchControllerWeb")               // unique bean name
@RequestMapping("/api/web/search")                  // unique base path
@RequiredArgsConstructor
public class SearchController {

    private final SimilarityService similarity;

    @GetMapping
    public List<SimilarProductDto> search(@RequestParam("q") String query,
                                          @RequestParam(defaultValue = "10") int k) {
        return similarity.searchByText(query, k);
    }
}
