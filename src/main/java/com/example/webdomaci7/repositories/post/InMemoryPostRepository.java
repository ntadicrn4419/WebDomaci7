package com.example.webdomaci7.repositories.post;

import com.example.webdomaci7.entities.Post;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryPostRepository implements IPostRepository{
    private static List<Post> posts = new CopyOnWriteArrayList<>();

    @Override
    public synchronized Post addPost(Post post) {
        post.setId(posts.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        post.setDatePublished(LocalDate.now().format(formatter));
        posts.add(post);
        return post;
    }

    @Override
    public List<Post> allPosts() {
        return new ArrayList<>(posts);
    }

    @Override
    public Post findPost(Integer id) {
        for (Post post: posts) {
            if(post.getId().equals(id)){
                return post;
            }
        }
        return null;
    }
}
