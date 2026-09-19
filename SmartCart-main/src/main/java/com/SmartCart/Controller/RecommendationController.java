package com.SmartCart.Controller;

import com.SmartCart.DTO.RecommendationResponse;
import com.SmartCart.Service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public RecommendationResponse recommend(
            @RequestParam String query
    ) {
        return recommendationService.recommend(query);
    }
}

