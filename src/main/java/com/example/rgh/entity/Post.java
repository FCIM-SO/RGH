package com.example.rgh.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "caption")
    private String caption;

    @Column(name = "location")
    private String location;

    @Column(name = "likes")
    private Integer likes;


    @ElementCollection(targetClass = String.class)
    @Column(name = "liked_user")
    private Set<String> likedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
