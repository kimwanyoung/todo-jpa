package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.src.todojpa.domain.dto.CommentCreateDto;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @Column
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public static Comment from(CommentCreateDto dto) {
        return Comment.builder()
                .contents(dto.getContents())
                .username(dto.getUsername())
                .build();
    }
}
