package me.jko.javatokotlin.repository;

import me.jko.javatokotlin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
