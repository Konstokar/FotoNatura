package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired; //Аннотация, позволяющая Spring разрешать и внедрять
// сотрудничающие компоненты в какой-либо компонент
import org.springframework.security.core.GrantedAuthority; // отражает разрешения выданные доверителю в масштабе всего
// приложения
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Сохраняет String представление полномочий,
// предоставленных объекту Authentication.
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // используется для получения данных, связанных
// с пользователем
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // Аннотация, объявляющая, что этот класс представляет собой
// сервис – компонент сервис-слоя

import java.util.List;
import java.util.stream.Collectors; // накапливает входные элементы в изменяемый контейнер результатов, дополнительно
// преобразовывая накопленный результат в окончательное представление после обработки всех входных элементов

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));

        return build(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }


    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }




}

