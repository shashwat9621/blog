package com.myproject.blog.repositories;

import com.myproject.blog.entities.Category;
import com.myproject.blog.entities.Post;
import com.myproject.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}