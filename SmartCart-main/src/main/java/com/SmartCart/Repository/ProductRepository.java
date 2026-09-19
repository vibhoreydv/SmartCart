package com.SmartCart.Repository;

import com.SmartCart.Entity.Product;
import com.SmartCart.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // “Fetch all Products that have at least one Tag from the given collection,
    //and eagerly load their tags in the same query.”
    @Query("""
                SELECT DISTINCT p
                FROM Product p
                JOIN FETCH p.tags t
                WHERE t IN :tags
            """)
    List<Product> findByTagsIn(@Param("tags") Collection<Tag> tags);
}

