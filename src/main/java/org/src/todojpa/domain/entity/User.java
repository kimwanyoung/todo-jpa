package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.src.todojpa.config.PasswordEncoder;
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

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

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
