package com.SmartCart.Service;

import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * IntentClassificationService
 * ============================================================
 *
 * Responsibility:
 *  - Accept raw user query text
 *  - Run OpenNLP intent classification
 *  - Return probability scores for ALL known intents
 *
 * This service:
 *  - Does NOT modify DB
 *  - Does NOT select products
 *  - Does NOT apply business rules
 *
 * It is a pure NLP inference layer.
 */
@Service
public class IntentClassificationService {

    private final DocumentCategorizerME categorizer;

    /**
     * Constructor injection ensures:
     *  - Model is loaded before service usage
     *  - Service is testable via mocks
     */
    public IntentClassificationService(DocumentCategorizerME categorizer) {
        this.categorizer = categorizer;
    }

    /**
     * Classifies a user query into all known intents
     * with confidence scores.
     *
     * @param query Raw user input text
     * @return Map of intent name -> confidence score (0.0 to 1.0)
     */
    public Map<String, Double> classifyIntents(String query) {

        String[] queries = query.split(" ");

        /*
         * OpenNLP returns probabilities in an array.
         * Each index corresponds to a category name.
         */
        double[] probabilities = categorizer.categorize(queries);

        Map<String, Double> intentConfidenceMap = new HashMap<>();

        /*
         * Convert array-based response into a readable map:
         *  intent_name -> probability
         */
        for (int i = 0; i < probabilities.length; i++) {
            String intentName = categorizer.getCategory(i);
            intentConfidenceMap.put(intentName, probabilities[i]);
        }

        return intentConfidenceMap;
    }

    /**
     * Returns the single best intent for a query.
     * Useful for quick decisions or fallbacks.
     *
     * @param query Raw user input
     * @return Intent name with highest probability
     */
    public String getBestIntent(String query) {

        String[] queries = query.split(" ");

        double[] probabilities = categorizer.categorize(queries);

        /*
         * OpenNLP internally finds the max probability
         * and returns the corresponding category name.
         */
        return categorizer.getBestCategory(probabilities);
    }
}

