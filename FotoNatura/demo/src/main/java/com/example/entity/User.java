package com.example.entity;

import com.example.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;

@Data // помогает создать все геттеры и сеттеры для объекта User
@Entity // указание того, что данный класс является сущностью БД
public class User {
    @Id // id колонки БД
    @GeneratedValue(strategy = GenerationType.IDENTITY) // указание, что данное свойство будет создаваться согласно
    // указанной стратегии (IDENTITY - использование встроенного в БД тип данных столбца)
    private Long id;
    @Column(nullable = false) // указание на колонку, которая отображается в свойсто сущности (nullable - равен NULL)
    private String name;
    @Column(unique = true, updatable = false) // updatable - необновляемое значение, unique - уникальное значение
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text") // columnDefinition - тип данных строки БД
    private String biography;
    @Column(length = 3000) // length - длина значения
    private String password;

    @ElementCollection(targetClass = ERole.class) // определение набора экземпляров основного типа или встраемого class
    // targetClass - класс, являющийся типом элемента набора
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) // определение таблицы, которая используется
    // для отображения наборов основных/встраиваемых типов
    /*
    * name - имя таблицы набора
    *
    * joinColumn - столбцы внешнего ключа таблицы набора, которые ссылаются на первичную таблицу объекта:
    *               @JoinColumn - отметка столбца в качестве столбца присоединения для ассоциации сущности/коллекции
    *               элементов (name - имя этого столбца)
    */
    private Set<ERole> role = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true) // отношение
    // "один ко многим"
    /*
    * cascade - каскадный тип (каскадное поведение - поведение, при котором характерно изменеие всех элементов, связанных
    * с основным объектом, при изменени последнего (например, при удаление пользователя автоматически удаляются все его
    * посты)
    * (CascadeType.ALL - все действия, которые мы выполняем с родительским объектом, нужно повторить и для его зависимых
    * объектов)
    *
    * fetch - как загружать дочерние сущности (FetchType.LAZY - при загрузке родительской сущности дочерние сущности не
    * загружаются)
    *
    * mappedBy - по какому полю дочерней сущности происходит соединение сущностей
    *
    * orphanRemoval - как должно происходить удаление сущностей (true - все объекты без ссылок на user будут удалены)
    */
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss") // указание, как форматировать значения в JSON-файлах (pattern - шаблон
    // форматирования)
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient // указание, что это свойство не нужно записывать
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
