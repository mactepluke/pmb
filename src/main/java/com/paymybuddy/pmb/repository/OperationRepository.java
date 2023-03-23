package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}