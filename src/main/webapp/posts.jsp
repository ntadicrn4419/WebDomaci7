<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Blog</title>
    <%@ include file="styles.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <button type="button" id="new-post-btn">New post</button>
</div>

<div class="container" id="new-post" style="display: none;">
    <form method="post" id="post-form">

            <div class="form-group">
                <label for="author">Author</label>
                <input class="form-control" id="author" name="author" placeholder="author">
            </div>
            <div class="form-group">
                <label for="title">Title</label>
                <input class="form-control" id="title" name="title" placeholder="title">
            </div>
            <div class="form-group">
                <label for="content">Content</label>
                <textarea class="form-control" id="content" name="content" rows="5"></textarea>
            </div>
            <button type="submit" class="btn btn-primary mb-2">Save post</button>

    </form>
</div>

<div class="container" id="created-posts">


</div>

<div class="container" id="single-post" style="display: none;">
    <h1 id="single-post-title"></h1>
    <h5 id="single-post-date-published"></h5>
    <h5 id="single-post-author"></h5>
    <p id="single-post-content"></p>
</div>


<div class="container" id="single-post-comments" style="display: none;">
    <h3>Comments:</h3>
</div>


<div class="container" id="new-comment" style="display: none;">
    <form method="post" id="comment-form">
            <h5>New comment</h5>
            <div class="form-group">
                <label for="comment-author">Name</label>
                <input class="form-control" id="comment-author" name="comment-author">
            </div>
            <div class="form-group">
                <label for="comment-content">Content</label>
                <textarea class="form-control" id="comment-content" name="comment-content" rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-primary mb-2">Submit comment</button>
    </form>
</div>

<script>

    fetch('/api/posts', {
        method: 'GET'
    }).then(response => {
        return response.json();
    }).then(posts => {
        for (const post of posts) {
            addPostElements(post)
        }
    })

    document.getElementById("new-post-btn").addEventListener("click", function() {
        document.getElementById("new-post-btn").style.display = "none";
        document.getElementById("created-posts").style.display = "none";
        document.getElementById("new-post").style.display = "block";
    })

    function addPostElements(post) {
        const createdPosts = document.getElementById('created-posts');

        const postCard = document.createElement('div');
        postCard.className = 'card';

        const postCardBody = document.createElement('div');
        postCardBody.className = 'card-body';

        const postCardTitle = document.createElement('h5');
        postCardTitle.className = 'card-title';
        postCardTitle.innerText = post.title;

        const postCardSubtitle = document.createElement('h6');
        postCardSubtitle.className = 'card-subtitle text-muted';
        postCardSubtitle.innerText = post.author;

        const postCardContent = document.createElement('p');
        postCardContent.className = 'card-text';
        postCardContent.innerText = post.content;

        const postCardFooter = document.createElement('div');
        postCardFooter.className = 'card-footer bg-white';

        const cardLinkButton = document.createElement('button');
        cardLinkButton.className = 'btn btn-link';
        cardLinkButton.id = 'card' + post.id;
        cardLinkButton.innerText = "More...";

        cardLinkButton.addEventListener("click", function() {

            document.getElementById("new-post-btn").style.display = "none";
            document.getElementById("created-posts").style.display = "none";

            document.getElementById('single-post-title').innerText = post.title;
            document.getElementById('single-post-date-published').innerText = post.datePublished;
            document.getElementById('single-post-author').innerText = post.author;
            document.getElementById('single-post-content').innerText = post.content;

            document.getElementById("comment-form").addEventListener('submit', function(e) {
                e.preventDefault();

                const commentAuthorElement = document.getElementById('comment-author');
                const commentContentElement = document.getElementById('comment-content');

                const author = commentAuthorElement.value;
                const content = commentContentElement.value;

                fetch('http://localhost:8080/api/posts/' + post.id + '/comments', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        author: author,
                        content: content,
                        postId: post.id
                    })
                }).then(response => {
                    return response.json();
                }).then(comment => {
                    addComment(comment);
                    commentAuthorElement.value = '';
                    commentContentElement.value = '';
                })
            })

            document.getElementById('single-post').style.display = 'block';
            document.getElementById('new-comment').style.display = 'block';


            fetch('/api/posts/' + post.id + '/comments', {
                method: 'GET'
            }).then(response => {
                return response.json();
            }).then(comments => {
                for (const comment of comments) {
                   addComment(comment);
                }
                document.getElementById("single-post-comments").style.display = 'block';
            })

        });

        postCardFooter.appendChild(cardLinkButton);

        postCardBody.appendChild(postCardTitle);
        postCardBody.appendChild(postCardSubtitle);
        postCardBody.appendChild(postCardContent);
        postCard.appendChild(postCardBody);
        postCard.appendChild(postCardFooter);

        createdPosts.appendChild(postCard);
    }

    document.getElementById("post-form").addEventListener('submit', function(e) {
        e.preventDefault();

        const postTitleElement = document.getElementById('title');
        const postAuthorElement = document.getElementById('author');
        const postContentElement = document.getElementById('content');

        const title = postTitleElement.value;
        const author = postAuthorElement.value;
        const content = postContentElement.value;

        const today = new Date();
        const datePublished = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();

        fetch('http://localhost:8080/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                author: author,
                title: title,
                content: content,
                datePublished: datePublished
            })
        }).then(response => {
            return response.json();
        }).then(post => {
            addPostElements(post)
            postTitleElement.value = '';
            postAuthorElement.value = '';
            postContentElement.value = '';
            document.getElementById("new-post").style.display = "none";
            document.getElementById("new-post-btn").style.display = "block";
            document.getElementById("created-posts").style.display = "block";

        })
    })

    function addComment(comment){
        const author = document.createElement('h3');
        author.innerText = comment.author;
        const content = document.createElement('h5');
        content.innerText = comment.content;
        document.getElementById("single-post-comments").appendChild(author);
        document.getElementById("single-post-comments").appendChild(content);
    }

</script>


</body>
</html>