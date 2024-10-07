package org.src.todojpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.src.todojpa.dto.ScheduleCreateDto;

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
}
