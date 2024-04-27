package com.ykotsiuba.clear_solution_test.repository;

import com.ykotsiuba.clear_solution_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByBirthDateBetween(
            LocalDate start,
            LocalDate end);
}
