package com.SmartCart.Service;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntentClassificationServiceIT {

    private static IntentClassificationService service;

    @BeforeAll
    static void setup() throws Exception {
        try (InputStream modelIn =
                     IntentClassificationServiceIT.class
                             .getResourceAsStream("/nlp/intent-doccat.bin")) {

            assertNotNull(modelIn, "Model file not found");

            DoccatModel model = new DoccatModel(modelIn);
            DocumentCategorizerME categorizer =
                    new DocumentCategorizerME(model);

            service = new IntentClassificationService(categorizer);
        }
    }

    @Test
    void shouldReturnRealProbabilities() {
        String text = "i want to propose marriage to my girlfriend";

        Map<String, Double> result = service.classifyIntents(text);

        assertFalse(result.isEmpty());
        assertEquals("[0.9840821993586049, 0.0016648789483262151, 7.961743721127366E-4, " +
                        "0.005379384989442254, 0.002017789256476696, 7.961743721127366E-4, " +
                        "7.961743721127366E-4, 7.961743721127366E-4, 0.0026274264162969643, " +
                        "0.001043623542402251]",
                result.values().toString());
        assertTrue(result.containsKey("proposal"));
    }

    @Test
    void shouldReturnRealProbabilities_falseInput() {
        String text = "";

        Map<String, Double> result = service.classifyIntents(text);

        assertFalse(result.isEmpty());
        // Every scoring is same when false input
        assertEquals("[0.10000000000000002, 0.10000000000000002, " +
                "0.10000000000000002, 0.10000000000000002, 0.10000000000000002, " +
                "0.10000000000000002, 0.10000000000000002, 0.10000000000000002, " +
                "0.10000000000000002, 0.10000000000000002]",
                result.values().toString());
        assertTrue(result.containsKey("proposal"));
    }

    @Test
    void testGetBestIntent() {
        String text = "i want to be at birthday";

        String bestIntent = service.getBestIntent(text);

        assertEquals("birthday", bestIntent);
    }

    @Test
    void testGetBestIntent_falseInput() {
        String text = "";

        String bestIntent = service.getBestIntent(text);

        assertEquals("proposal", bestIntent);
    }
}
