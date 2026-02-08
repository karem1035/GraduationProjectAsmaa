package com.example.Toda.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tourguide")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourGuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bio;

    @ElementCollection
    @CollectionTable(name = "tourguide_languages", joinColumns = @JoinColumn(name = "tourguide_id"))
    @Column(name = "language_code")
    private List<String> languages;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private UserEntity user;
}
