// 01.  - M24W0517 - Hewa Pathiranage Hashendra Dilan Nawarathna
// 02.  -M24W0383 - Bogati Surendra



package com.example.safedrivebackend.Entity

import jakarta.persistence.*

@Entity
data class Driver(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val address: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var frontImagePath: String? = null,
    var backImagePath: String? = null
)
