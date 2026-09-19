package com.SmartCart.DTO;

import java.util.List;

public record RecommendationResponse(
        String query,
        String primaryIntent,
        double confidence,
        List<ScoredProduct> products
) {}

