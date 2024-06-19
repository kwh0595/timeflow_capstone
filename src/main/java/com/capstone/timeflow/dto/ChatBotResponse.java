package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChatBotResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private Choice[] choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private String text;
    }
}