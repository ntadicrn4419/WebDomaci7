package com.example.webdomaci7.repositories.post;

import com.example.webdomaci7.entities.Post;

import java.util.List;

public interface IPostRepository {
    public Post addPost(Post post);
    public List<Post> allPosts();
    public Post findPost(Integer id);
}
