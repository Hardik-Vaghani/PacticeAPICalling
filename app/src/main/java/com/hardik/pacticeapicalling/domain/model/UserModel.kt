package com.hardik.pacticeapicalling.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hardik.pacticeapicalling.data.remote.dto.UserDto

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey val id: Int,
    val city: String,
    val companyName: String,
    val email: String,
    val lat: String,
    val lng: String,
    val name: String,
    val phone: String,
    val street: String,
    val suite: String,
    val username: String,
    val website: String,
    val zipcode: String
                    )

fun UserModel.toUserDto(): UserDto {
    return UserDto(
        address = UserDto.Address(
            city = city,
            street = street,
            suite = suite,
            geo = UserDto.Address.Geo(lat = lat, lng = lng),
            zipcode = zipcode,
                                 ),
        company = UserDto.Company(bs = "", catchPhrase = "", name = companyName),
        email = email,
        id = id,
        name = name,
        phone = phone,
        username = username,
        website = website,
                  )
}