package com.example.safedrivebackend.controllers

import com.example.safedrivebackend.services.DriverService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/user")
class AuthController(private val userService: DriverService) {

    data class RegisterRequest(val name: String, val email: String, val password: String)

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        return try {
            userService.registerUser(request.name, request.email, request.password)
            ResponseEntity.ok("User registered successfully")
        } catch (e: Exception) {
            ResponseEntity.status(400).body("Error: ${e.message}")
        }
    }
}
