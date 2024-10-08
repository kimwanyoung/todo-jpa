package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.src.todojpa.domain.dto.UserUpdateDto;

import java.util.Objects;

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

    @Column(unique = true)
    private String email;

    public void update(String name, String email) {
        if(name != null) {
            this.name = name;
        }

        if(email != null) {
            this.email = email;
        }
    }

    public boolean checkId(Long id) {
        return Objects.equals(this.id, id);
    }
}
