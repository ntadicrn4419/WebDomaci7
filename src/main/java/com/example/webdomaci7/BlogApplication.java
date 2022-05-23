package com.example.webdomaci7;

import com.example.webdomaci7.repositories.comment.ICommentRepository;
import com.example.webdomaci7.repositories.comment.InMemoryCommentRepository;
import com.example.webdomaci7.repositories.comment.MySqlCommentRepository;
import com.example.webdomaci7.repositories.post.IPostRepository;
import com.example.webdomaci7.repositories.post.InMemoryPostRepository;
import com.example.webdomaci7.repositories.post.MySqlPostRepository;
import com.example.webdomaci7.services.PostAndCommentService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class BlogApplication extends ResourceConfig {

    public BlogApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
//                this.bind(InMemoryPostRepository.class).to(IPostRepository.class).in(Singleton.class);
//                this.bind(InMemoryCommentRepository.class).to(ICommentRepository.class).in(Singleton.class);
                this.bind(MySqlPostRepository.class).to(IPostRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(ICommentRepository.class).in(Singleton.class);
                this.bindAsContract(PostAndCommentService.class);
            }
        };

        register(binder);
        packages("com.example.webdomaci7.resources");
    }
}