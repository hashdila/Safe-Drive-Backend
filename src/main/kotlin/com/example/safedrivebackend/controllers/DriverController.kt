package com.example.safedrivebackend.controllers

import com.example.safedrivebackend.dto.LoginRequest
import com.example.safedrivebackend.services.DriverService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*





@RestController
@RequestMapping("/api/user")
class AuthController(
    private val userService: DriverService,
    private val driverService: DriverService
) {

    data class RegisterRequest(val name: String, val email: String, val password: String ,val address: String,val phoneNumber: String, val dateOfBirth: String)

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
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        val driver = driverService.loginDriver(loginRequest.email, loginRequest.password)
        return if (driver != null) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }


}
