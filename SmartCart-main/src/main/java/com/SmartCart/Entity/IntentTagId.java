package com.SmartCart.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentTagId implements Serializable {

    @Column(name = "intent_id")
    private Long intentId;

    @Column(name = "tag_id")
    private Long tagId;
}
