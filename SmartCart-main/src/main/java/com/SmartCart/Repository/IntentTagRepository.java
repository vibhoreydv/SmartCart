package com.SmartCart.Repository;

import com.SmartCart.Entity.Intent;
import com.SmartCart.Entity.IntentTag;
import com.SmartCart.Entity.IntentTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IntentTagRepository
        extends JpaRepository<IntentTag, IntentTagId> {

    /**
     * JPQL note:
     * Although a collection of Intent entities is passed as a parameter,
     * Hibernate automatically translates this into an SQL IN clause using
     * their primary keys (intent_id).
     * <p>
     * Example:
     * WHERE it.intent IN :intents  -->  WHERE intent_id IN (1, 2, 3)
     */
    @Query("""
              SELECT it
              FROM IntentTag it
              JOIN FETCH it.tag
              JOIN it.intent i
              WHERE i.name IN :intentNames
            """)
    List<IntentTag> findByIntentNameIn(
            @Param("intentNames") Set<String> intentNames);

}
