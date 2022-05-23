package com.example.webdomaci7.repositories.comment;

import com.example.webdomaci7.entities.Comment;

import java.util.List;

public interface ICommentRepository {
    public Comment addComment(Integer postId, Comment comment);
    public List<Comment> allPostComments(Integer postId);
    public Comment findComment(Integer commentId);
}
