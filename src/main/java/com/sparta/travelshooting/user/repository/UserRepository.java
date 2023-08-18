package com.sparta.travelshooting.user.repository;

import com.sparta.travelshooting.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
