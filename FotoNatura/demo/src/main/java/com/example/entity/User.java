package com.example.entity;

import com.example.entity.enums.ERole;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String name, username, lastname, email, biography, password;

    private Set<ERole> role = new HashSet<>();
    private List<Post> posts = new ArrayList<>();

    private LocalDateTime createdDate;

    @PrePersist // задавание значение этого атрибута до того, как новая запись будет сделана в базе данных
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
