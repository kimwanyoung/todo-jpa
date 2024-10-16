package org.src.todojpa.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.src.todojpa.domain.UserRole;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean validateWriterByUserId(Long id){
        return user.checkId(id);
    }
}
