package com.SmartCart.Config;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class OpenNlpConfig {

    @Bean
    public DoccatModel intentDoccatModel() throws Exception {
        ClassPathResource resource = new ClassPathResource("nlp/intent-doccat.bin");
        try (InputStream modelIn = resource.getInputStream()) {
            return new DoccatModel(modelIn);
        }
    }

    @Bean
    public DocumentCategorizerME intentDocumentCategorizer(DoccatModel intentDoccatModel) {
        return new DocumentCategorizerME(intentDoccatModel);
    }
}

