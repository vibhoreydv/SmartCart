package com.SmartCart.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "intent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "display_name", length = 150)
    private String displayName;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "intent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IntentTag> intentTags = new HashSet<>();
}
