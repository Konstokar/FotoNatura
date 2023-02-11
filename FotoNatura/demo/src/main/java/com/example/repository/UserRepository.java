package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;// JpaRepository - интерфейс фреймворка Spring Data,
// предоставляющий набор стандартных методов JPA для работы с базой данных
import org.springframework.stereotype.Repository;

import java.util.Optional; // Класс, предоставляющий решения на уровне типа-обертки для обеспечения удобства обработки
// возможных null-значений

@Repository // показывает, что класс применяется для работы с поиском, а также для получения и хранения данных
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
}
