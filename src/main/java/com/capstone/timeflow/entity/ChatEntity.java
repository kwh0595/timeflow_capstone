package com.capstone.timeflow.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    private String sender;


    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDate;

    @Builder
    public ChatEntity(TeamEntity teamId, String sender, String message) {
        this.team = teamId;
        this.sender = sender;
        this.message = message;
        this.sendDate = LocalDateTime.now();
    }

    public static ChatEntity createChat(TeamEntity teamId, String sender, String message) {
        return ChatEntity.builder()
                .teamId(teamId)
                .sender(sender)
                .message(message)
                .build();
    }
}