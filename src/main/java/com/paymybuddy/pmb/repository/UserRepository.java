package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
