package me.jko.javatokotlin.service

import me.jko.javatokotlin.entity.User
import me.jko.javatokotlin.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(name: String, email: String): User {
        val user = User()
        user.name = name
        user.email = email

        return userRepository.save(user)
    }

    fun getUserById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    fun updateUsername(id: Long, updateName: String): User {
        val user = userRepository.findById(id)
            .orElseThrow {
                RuntimeException(
                    "User not found with id: $id"
                )
            }

        user.name = updateName

        return userRepository.save(user)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
