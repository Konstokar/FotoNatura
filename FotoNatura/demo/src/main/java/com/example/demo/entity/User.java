package com.example.demo.entity;

import com.example.demo.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // UserDetails - интерфейс, который предоставляет
// геттеры, которые гарантируют not- null результаты аутентификационной информации, такие как имя пользователя, пароль,
// предоставленные полномочия и является ли учетная запись пользователя заблокированной или нет

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * SECURITY
     */



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { // истек ли срок действия учетной записи пользователя?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // заблокирован или разблокирован пользователь?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // истек ли срок действия учетных данных пользователя (пароля)?
        return true;
    }

    @Override
    public boolean isEnabled() { //  включен или отключен пользователь?
        return true;
    }
}