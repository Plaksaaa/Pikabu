package com.plaxa.pikabu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_tag")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void setPost(Post post) {
        this.post = post;
        this.post.getPostTags().add(this);
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        this.tag.getPostTags().add(this);
    }
}
