package com.himank.dailydigest.respositories;

import com.himank.dailydigest.domain.Post;
import com.himank.dailydigest.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PostRepositoryTests {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;


    @Test
    void whenFindById_thenReturnPost() {
        // given
        User user1 = new User("user1", "user1@test.com");
        User user = entityManager.persistAndFlush(user1);
        Post jpaPost = new Post("JPA Entity Graph In Action", user);
        Post post = entityManager.persistAndFlush(jpaPost);
        // when
        Post found = postRepository.getById(post.getId());

        // then
        assertThat(found.getSubject())
                .isEqualTo("JPA Entity Graph In Action");
    }


    @Test
    void whenFindByUser_thenReturnPosts() {
        // given
        User user2 = new User("user2", "user2@test.com");
        User user = entityManager.persistAndFlush(user2);
        Post jpaPost = new Post("JPA Entity Graph In Action 2", user);
        Post post = entityManager.persistAndFlush(jpaPost);
        // when
        List<Post> foundPosts = postRepository.getAllByUser(user);

        // then
        assertThat(foundPosts).hasSize(1);
        assertThat(foundPosts.get(0).getSubject())
                .isEqualTo("JPA Entity Graph In Action 2");
    }


}
