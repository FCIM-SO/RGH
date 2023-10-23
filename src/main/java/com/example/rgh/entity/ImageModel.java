package com.example.rgh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Table(name = "image_model")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name="image_bytes")
    private byte[] imageBytes;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "post_id")
    private Long postId;
}
