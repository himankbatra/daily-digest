package com.himank.dailydigest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "post-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("comments"),
        }
)
@NamedEntityGraph(
        name = "post-entity-graph-with-comment-users",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "comments-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("user")
                        }
                )
        }
)
@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Post(String subject, User user) {
        this.subject = subject;
        this.user = user;
    }

}