package com.example.webdomaci7.repositories.comment;

import com.example.webdomaci7.entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements ICommentRepository{

    private static List<Comment> comments = new CopyOnWriteArrayList<>();
    @Override
    public synchronized Comment addComment(Integer postId, Comment comment) {
        comment.setPostId(postId);
        comment.setId(comments.size());
        comments.add(comment);
        return comment;
    }

    @Override
    public List<Comment> allPostComments(Integer postId) {
        List<Comment> postComments = new ArrayList<>();
        for(Comment comment: comments){
            if(comment.getPostId().equals(postId)){
                postComments.add(comment);
            }
        }
        return postComments;
    }

    @Override
    public Comment findComment(Integer commentId) {
        for(Comment comment: comments){
            if(comment.getId().equals(commentId)){
                return comment;
            }
        }
        return null;
    }
}
