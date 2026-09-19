package com.SmartCart.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "intent_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentTag {

    @EmbeddedId
    private IntentTagId id = new IntentTagId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("intentId")
    @JoinColumn(name = "intent_id")
    private Intent intent;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal weight = BigDecimal.ONE;
}
