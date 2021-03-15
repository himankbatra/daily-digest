package com.himank.dailydigest.api.rest.resources;

import com.himank.dailydigest.domain.Post;
import com.himank.dailydigest.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostResource {

    private final PostService postService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> get(@PathVariable("id") final Long id) {
        Optional<Post> optionalPost =
                this.postService.findById(id);
        return optionalPost.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
