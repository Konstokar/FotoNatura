package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob // указание на большие объекты
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageBytes;
    @JsonIgnore // пометка свойства/списка свойств, которые следует игнорировать
    private Long userId;
    @JsonIgnore
    private Long postId;
}
