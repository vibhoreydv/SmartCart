package com.SmartCart.DTO;

import java.math.BigDecimal;

public record ScoredProduct(
        Long productId,
        String title,
        BigDecimal price,
        BigDecimal discount,
        double score
) {}

