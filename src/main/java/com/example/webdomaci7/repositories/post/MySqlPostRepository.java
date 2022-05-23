package com.example.webdomaci7.repositories.post;

import com.example.webdomaci7.entities.Post;
import com.example.webdomaci7.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPostRepository extends MySqlAbstractRepository implements IPostRepository{

    @Override
    public Post addPost(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO posts (author, title, content, datePublished) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, post.getAuthor());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setString(4, post.getDatePublished());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                post.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return post;
    }

    @Override
    public List<Post> allPosts() {
        List<Post> posts = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from posts");
            while (resultSet.next()) {
                Post p = new Post();
                p.setId(resultSet.getInt("id"));
                p.setAuthor(resultSet.getString("author"));
                p.setTitle(resultSet.getString("title"));
                p.setContent(resultSet.getString("content"));
                Date date = resultSet.getDate("datePublished");
                p.setDatePublished(date.toString());
                posts.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return posts;
    }

    @Override
    public Post findPost(Integer id) {
        Post post = new Post();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM posts where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int postId = resultSet.getInt("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date date = resultSet.getDate("datePublished");

                post.setId(postId);
                post.setAuthor(author);
                post.setTitle(title);
                post.setContent(content);
                post.setDatePublished(date.toString());
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return post;
    }
}
