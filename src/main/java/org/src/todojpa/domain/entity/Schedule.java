package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private User user;

    public void update(ScheduleUpdateDto dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    public void validateUserById(Long id){
        if(!user.checkId(id)) {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
