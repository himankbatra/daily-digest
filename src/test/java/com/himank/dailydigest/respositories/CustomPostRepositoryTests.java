package com.himank.dailydigest.respositories;

import com.himank.dailydigest.domain.Comment;
import com.himank.dailydigest.domain.Post;
import com.himank.dailydigest.domain.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CustomPostRepositoryTests {

    private static CustomPostRepository customPostRepository = null;

    @BeforeAll
    static void once() {
        customPostRepository = new CustomPostRepository();
    }

    @Disabled
    @Test
    void find() {
        assertThrows(LazyInitializationException.class, () -> {
            Post post = customPostRepository.find(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNull(email);
        });
    }

    @Test
    void findWithEntityGraph() {
        Post post = customPostRepository.findWithEntityGraph(1L);
        assertNotNull(post.getUser());
        String email = post.getUser().getEmail();
        assertNotNull(email);
    }

    @Test
    void findWithEntityGraph_Comment_Without_User() {

        assertThrows(LazyInitializationException.class, () -> {
            Post post = customPostRepository.findWithEntityGraph(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNotNull(email);
            assertNotNull(post.getComments());
            assertEquals(post.getComments().size(), 2);
            Comment comment = post.getComments().get(0);
            assertNotNull(comment);
            User user = comment.getUser();
            String userEmail = user.getEmail();
        });
    }

    @Test
    void findWithEntityGraph2_Comment_With_User() {
        Post post = customPostRepository.findWithEntityGraph2(1L);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "user2@test.com");
    }

    @AfterAll
    static void destroy() {
        customPostRepository.clean();
    }


}