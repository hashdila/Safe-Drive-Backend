// 01.  - M24W0517 - Hewa Pathiranage Hashendra Dilan Nawarathna
// 02.  -M24W0383 - Bogati Surendra


package com.example.safedrivebackend.repositories


import com.example.safedrivebackend.Entity.Driver


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository : JpaRepository<Driver, String> {
    fun findByEmail(email: String): Driver?
//    fun findByLatitudeAndLongitude(latitude: Double, longitude: Double): Driver?
    @Query("SELECT d FROM Driver d WHERE d.latitude = :latitude AND d.longitude = :longitude")
    fun findByLatitudeAndLongitude(
    @Param("latitude") latitude: Double,
    @Param("longitude") longitude: Double
    ): Driver?
}

