package com.myproject.blog.services;

import com.myproject.blog.dto.CategoryDto;
import com.myproject.blog.dto.PostDto;
import com.myproject.blog.entities.Category;
import com.myproject.blog.entities.Post;
import com.myproject.blog.entities.User;
import com.myproject.blog.exceptions.ResouceNotFoundException;
import com.myproject.blog.payloads.PostResponse;
import com.myproject.blog.repositories.CategoryRepo;
import com.myproject.blog.repositories.PostRepo;
import com.myproject.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResouceNotFoundException("User","id",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category","id", categoryId));
        Post post = this.dtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = this.postRepo.save(post);
        return this.postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.postToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else {
            sort=Sort.by(sortBy).descending();
        }
        PageRequest p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos =posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
        PostResponse postResponse =new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","post id",postId));
        return this.postToDto(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category","id",categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResouceNotFoundException("User","id",userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    private Post dtoToPost(PostDto postDto)
    {
        Post post  = this.modelMapper.map(postDto, Post.class);
        return post;
    }
    private  PostDto postToDto(Post post)
    {
        PostDto postDto = this.modelMapper.map(post,PostDto.class);
        return postDto;

    }

}
