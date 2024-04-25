package com.ykotsiuba.clear_solution_test.repository;

import com.ykotsiuba.clear_solution_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
