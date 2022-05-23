package com.example.webdomaci7.resources;

import com.example.webdomaci7.entities.Comment;
import com.example.webdomaci7.entities.Post;
import com.example.webdomaci7.services.PostAndCommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
public class PostAndCommentResource {

    @Inject
    private PostAndCommentService postAndCommentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> allPosts() { return this.postAndCommentService.allPosts(); }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post createPost(@Valid Post post) {
        return this.postAndCommentService.addPost(post);
    }

    @POST
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment createComment(@PathParam("id") Integer id, @Valid Comment comment) {
        return this.postAndCommentService.addComment(id, comment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@PathParam("id") Integer id) {
        return this.postAndCommentService.findPost(id);
    }

    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> findPostComments(@PathParam("id") Integer id) {
        return this.postAndCommentService.findPostComments(id);
    }

}