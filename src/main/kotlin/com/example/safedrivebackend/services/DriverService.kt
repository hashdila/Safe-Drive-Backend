package com.example.safedrivebackend.services

import com.example.safedrivebackend.Entity.Driver
import com.example.safedrivebackend.repositories.DriverRepository
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service



@Service
class DriverService(private val userRepository: DriverRepository) {

//    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(name: String, email: String, password: String): Driver {
        // Check if the user already exists
        if (userRepository.findByEmail(email) != null) {
            throw Exception("User with this email already exists")
        }

        // Hash the password before saving
//        val hashedPassword = passwordEncoder.encode(password)
        val user = Driver(name = name, email = email, password = password)

        return userRepository.save(user) // Save to database
    }
}
