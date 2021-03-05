package com.himank.dailydigest.respositories;

import com.himank.dailydigest.domain.Post;
import com.himank.dailydigest.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    // referencing a named entity graph
    @EntityGraph(value = "post-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    Post getById(Long id);

    // ad-hoc entity graph
    @EntityGraph(attributePaths = { "subject","user" })
    List<Post> getAllByUser(User user);

}
