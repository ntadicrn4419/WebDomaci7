package com.example.webdomaci7.services;

import com.example.webdomaci7.entities.Comment;
import com.example.webdomaci7.entities.Post;
import com.example.webdomaci7.repositories.comment.ICommentRepository;
import com.example.webdomaci7.repositories.post.IPostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostAndCommentService {

    @Inject
    private IPostRepository postRepository;
    @Inject
    ICommentRepository commentRepository;

    public List<Post> allPosts() {
        return this.postRepository.allPosts();
    }

    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public Post findPost(Integer postId) {
        return this.postRepository.findPost(postId);
    }

    public Comment addComment(Integer postId, Comment comment) {
        return this.commentRepository.addComment(postId, comment);
    }

    public List<Comment> findPostComments(Integer postId){
        return this.commentRepository.allPostComments(postId);
    }
}
