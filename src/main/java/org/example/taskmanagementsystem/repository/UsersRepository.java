package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.Enums.RolesType;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    List<Users> findByRole(RolesType role);

}
