package com.himank.dailydigest.respositories;

import com.himank.dailydigest.domain.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DirtiesContext
public class PostRepositoryTests {


    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-init.sql"));
        }
    }


    @Autowired
    private PostRepository postRepository;


    @Test
    void whenFindById_thenReturnPost() {
        // given

        // when
        Optional<Post> optionalPost = postRepository.findById(1L);
        assertThat(optionalPost.isPresent()).isTrue();
        Post found = optionalPost.get();
        // then
        assertThat(found.getSubject())
                .isEqualTo("JPA Entity Graph In Action");
        assertThat(found.getComments().get(0)).isNotNull();
        assertThat(found.getComments()).hasSize(2);
    }


    @Test
    void whenFindByUser_thenReturnPosts() {
        // given

        // when
        List<Post> foundPosts = postRepository.getAllByUserId(1L);

        // then
        assertThat(foundPosts).hasSize(1);
        assertThat(foundPosts.get(0).getSubject())
                .isEqualTo("JPA Entity Graph In Action");
        assertThat(foundPosts.get(0).getComments()).hasSize(2);
    }


}
