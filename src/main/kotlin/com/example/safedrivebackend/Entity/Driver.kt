package com.example.safedrivebackend.Entity

import jakarta.persistence.*



@Entity
data class Driver(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String // Store hashed password, not plain text!
)
