package com.SmartCart.Cache;

import com.SmartCart.Entity.IntentTag;
import com.SmartCart.Repository.IntentTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IntentTagCacheService {

    private final IntentTagRepository intentTagRepository;

    @Cacheable(value = "intentTagCache", key = "#intentNames")
    public List<IntentTag> getIntentTags(Set<String> intentNames) {

        return intentTagRepository.findByIntentNameIn(intentNames);
    }
}

