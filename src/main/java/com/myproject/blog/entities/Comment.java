package com.myproject.blog.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    @ManyToOne
    private Post post;
}
