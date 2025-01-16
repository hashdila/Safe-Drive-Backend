package com.example.safedrivebackend.controllers

import com.example.safedrivebackend.Entity.Driver
import com.example.safedrivebackend.services.DriverService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/user")
class AuthController(
    private val userService: DriverService,
    private val driverService: DriverService
) {

    data class RegisterRequest(val name: String, val email: String, val password: String ,val address: String,val phoneNumber: String, val dateOfBirth: String)

    // Login request DTO
    data class LoginRequest(val email: String, val password: String)

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        return try {
            userService.registerDriver(request.name, request.email, request.password,request.address, request.phoneNumber, request.dateOfBirth)
            ResponseEntity.ok("User registered successfully")
        } catch (e: Exception) {
            ResponseEntity.status(400).body("Error: ${e.message}")
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, Any>> {
        val driver = driverService.loginDriver(loginRequest.email, loginRequest.password)
        return if (driver != null) {
            // Create a map with user details to send back in the response
            val driverResponse = mapOf(
                "name" to driver.name,
                "email" to driver.email,
                "address" to driver.address,
                "phoneNumber" to driver.phoneNumber,
                "dateOfBirth" to driver.dateOfBirth
            )
            ResponseEntity.ok(driverResponse)  // Return the user data as part of the response
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }
    }

    @PostMapping("/verify")
    fun verifyDriver(
        @RequestParam email: String,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam frontImage: MultipartFile,
        @RequestParam backImage: MultipartFile
    ): ResponseEntity<String> {
        return try {
            driverService.updateVerificationDetails(email, latitude, longitude, frontImage, backImage)
            ResponseEntity.ok("Verification details updated successfully")
        } catch (e: Exception) {
            ResponseEntity.status(400).body("Error: ${e.message}")
        }
    }

    @GetMapping("/all-locations")
    fun getAllLocations(): ResponseEntity<List<Map<String, Double>>> {
        return ResponseEntity.ok(driverService.getAllDriverLocations())
    }
//    @GetMapping("/driver-by-location")
//    fun getDriverByLocation(
//        @RequestParam latitude: Double,
//        @RequestParam longitude: Double
//    ): ResponseEntity<Driver> {
//        val driver = driverService.getDriverByLocation(latitude, longitude)
//        return if (driver != null) {
//            ResponseEntity.ok(driver)
//        } else {
//            ResponseEntity.status(404).body(null)
//        }
//    }



    @GetMapping("/details/location")
    fun getDriverByLocation(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): ResponseEntity<Driver> {
        return try {
            val driver = driverService.getDriverByLocation(latitude, longitude)
            ResponseEntity.ok(driver)
        } catch (e: Exception) {
            ResponseEntity.status(404).body(null)
        }
    }





    @GetMapping("/details/{email}")
    fun getDriverDetails(@PathVariable email: String): ResponseEntity<Driver> {
        val driver = driverService.getDriverDetails(email)
        return ResponseEntity.ok(driver)
    }




}
