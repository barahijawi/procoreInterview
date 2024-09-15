package com.example.procoreinterview.domain

data class PockemonCard(
    val id: String,
    val name: String,
    val hp: String,
    val imageUrlSmall: String,   // smaller image
    val imageUrlLarge: String,   // larger image
    val superType: String?,       // High-level classification (e.g., "Pokémon", "Trainer")
    val types: List<String>,     // Elemental types (converted from comma-separated string)
    val subTypes: List<String>   // Subtypes (converted from comma-separated string)
)
