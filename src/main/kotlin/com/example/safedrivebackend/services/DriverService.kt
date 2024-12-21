package com.example.safedrivebackend.services

import com.example.safedrivebackend.Entity.Driver
import com.example.safedrivebackend.repositories.DriverRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.stereotype.Service
import java.text.SimpleDateFormat


@Service
class DriverService(private val driverRepository: DriverRepository) {


    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerDriver(name: String, email: String, password: String, address: String, phoneNumber: String, dateOfBirth: String): Driver {

        // Check if the user already exists
        if (driverRepository.findByEmail(email) != null) {
            throw Exception("User with this email already exists")
        }



        // Hash the password before saving
        val hashedPassword = passwordEncoder.encode(password)

        val driver = Driver(name = name, email = email, password = hashedPassword, address= address, phoneNumber= phoneNumber, dateOfBirth= dateOfBirth)

        return driverRepository.save(driver) // Save to database
    }

    fun loginDriver(email: String, password: String): Driver? {
        val driver = driverRepository.findByEmail(email)
        return if (driver != null && passwordEncoder.matches(password, driver.password)) {
            driver
        } else {
            null
        }
    }


}
