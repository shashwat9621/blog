package com.myproject.blog.services;

import com.myproject.blog.dto.CommentDto;
import com.myproject.blog.entities.Comment;
import com.myproject.blog.entities.Post;
import com.myproject.blog.exceptions.ResouceNotFoundException;
import com.myproject.blog.repositories.CommentRepo;
import com.myproject.blog.repositories.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","post id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResouceNotFoundException("Comment","comment id",commentId));
        this.commentRepo.delete(com);

    }
}
