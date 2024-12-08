package com.example.safedrivebackend.repositories

import com.example.safedrivebackend.Entity.Driver

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository : JpaRepository<Driver, String> {
    fun findByEmail(email: String): Driver?
}
