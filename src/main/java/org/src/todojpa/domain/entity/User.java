package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.src.todojpa.domain.dto.UserUpdateDto;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    public void update(UserUpdateDto dto) {
        if(dto.getName() != null) {
            this.name = dto.getName();
        }

        if(dto.getEmail() != null) {
            this.email = dto.getEmail();
        }
    }
}
