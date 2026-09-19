package com.SmartCart.Service;

import com.SmartCart.Cache.IntentTagCacheService;
import com.SmartCart.DTO.RecommendationResponse;
import com.SmartCart.DTO.ScoredProduct;
import com.SmartCart.Entity.IntentTag;
import com.SmartCart.Entity.Product;
import com.SmartCart.Entity.Tag;
import com.SmartCart.Repository.IntentRepository;
import com.SmartCart.Repository.IntentTagRepository;
import com.SmartCart.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {


    private static final double CONFIDENCE_THRESHOLD = 0.40;

    private final IntentClassificationService intentService;
    private final IntentRepository intentRepository;
    private final IntentTagRepository intentTagRepository;
    private final ProductRepository productRepository;
    private final IntentTagScoringService tagScoringService;
    private final ProductScoringService productScoringService;
    private final IntentTagCacheService intentTagCacheService;

    public RecommendationResponse recommend(String query) {

        MDC.put("reqId", UUID.randomUUID().toString());

        log.info("Recommendation request received. query='{}'", query);

        Map<String, Double> intentConf = intentService.classifyIntents(query);

        String primaryIntent = intentService.getBestIntent(query);

        double confidence = intentConf.getOrDefault(primaryIntent, 0.0);

        log.info(
                "NLP result: primaryIntent='{}', confidence={}, allIntents={}",
                primaryIntent, confidence, intentConf
        );

        if (confidence < CONFIDENCE_THRESHOLD) {
            log.warn("Fallback triggered due to low confidence");
            return fallbackResponse(query);
        }

        List<IntentTag> intentTags =
                intentTagCacheService.getIntentTags(intentConf.keySet());

        log.debug("Loaded intent-tag mappings count={}", intentTags.size());

//        Map<Tag, Double> tagScores =
//                tagScoringService.computeTagScores(intentConf, intentTags);
//
//        log.debug("Computed tag scores={}", tagScores);
//
//        List<Product> products =
//                productRepository.findByTagsIn(tagScores.keySet());
//
//        log.info("Fetched {} candidate products", products.size());
//
//        List<ScoredProduct> ranked = rank(products, tagScores);

        // replace null with ranked
        return new RecommendationResponse(
                query, primaryIntent, confidence, null
        );
    }

    private ScoredProduct toScoredProduct(
            Product product,
            Map<Tag, Double> tagScores
    ) {
        double score = 0.0;
               // productScoringService
                      //  .computeFinalProductScore(product, tagScores);

        return new ScoredProduct(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getBaseDiscountPercent(),
                score
        );
    }

    private RecommendationResponse fallbackResponse(String query) {
        return new RecommendationResponse(
                query,
                "unknown",
                0.0,
                List.of()
        );
    }
}

