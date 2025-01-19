// 01.  - M24W0517 - Hewa Pathiranage Hashendra Dilan Nawarathna
// 02.  -M24W0383 - Bogati Surendra


package com.example.safedrivebackend.services

import com.example.safedrivebackend.Entity.Driver
import com.example.safedrivebackend.repositories.DriverRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption


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

    private fun saveFile(file: MultipartFile, uploadDir: String): String {
        val uploadPath = Path.of(uploadDir)
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath)
        val fileName = System.currentTimeMillis().toString() + "_" + file.originalFilename
        val filePath = uploadPath.resolve(fileName)
        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        return filePath.toString()
    }

    fun updateVerificationDetails(
        email: String,
        latitude: Double,
        longitude: Double,
        frontImage: MultipartFile,
        backImage: MultipartFile
    ) {
        val driver = driverRepository.findByEmail(email) ?: throw Exception("Driver not found")
        val frontImagePath = saveFile(frontImage, "uploads/front")
        val backImagePath = saveFile(backImage, "uploads/back")
        driver.latitude = latitude
        driver.longitude = longitude
        driver.frontImagePath = frontImagePath
        driver.backImagePath = backImagePath
        driverRepository.save(driver)
    }

    fun getDriverDetails(email: String): Driver {
        return driverRepository.findByEmail(email) ?: throw Exception("Driver not found")
    }

    fun getAllDriverLocations(): List<Map<String, Double>> {
        return driverRepository.findAll().mapNotNull { driver ->
            if (driver.latitude != null && driver.longitude != null) {
                mapOf("latitude" to driver.latitude!!, "longitude" to driver.longitude!!)
            } else null
        }
    }

//    fun getDriverByLocation(latitude: Double, longitude: Double): Driver? {
//        return driverRepository.findByLatitudeAndLongitude(latitude, longitude)
//    }

    fun getDriverByLocation(latitude: Double, longitude: Double): Driver {
        return driverRepository.findByLatitudeAndLongitude(latitude, longitude)
            ?: throw Exception("No driver found at the specified location")
    }







}
