package org.src.todojpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.src.todojpa.entity.Schedule;

@SpringBootApplication
public class TodoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoJpaApplication.class, args);
    }

}
