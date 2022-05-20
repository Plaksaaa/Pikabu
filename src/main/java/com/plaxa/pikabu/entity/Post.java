package com.plaxa.pikabu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "votes")
    private Integer voteCount;

    private Instant createdDate;

//    private String pictureUrl;

    private String description;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostTag> postTags = new ArrayList<>();
}
