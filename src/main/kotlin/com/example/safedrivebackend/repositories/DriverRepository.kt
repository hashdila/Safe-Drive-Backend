package com.example.safedrivebackend.repositories

import com.example.safedrivebackend.Entity.Driver
import org.springframework.data.jpa.repository.JpaRepository

interface DriverRepository : JpaRepository<Driver, Long> {
    fun findByEmail(email: String): Driver?
}
