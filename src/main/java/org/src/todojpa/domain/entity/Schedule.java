package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.src.todojpa.domain.dto.ScheduleCreateDto;
import org.src.todojpa.domain.dto.ScheduleUpdateDto;

@Getter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    public static Schedule from(ScheduleCreateDto dto) {
        return Schedule.builder()
                .username(dto.getUsername())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .build();
    }

    public void update(ScheduleUpdateDto dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
