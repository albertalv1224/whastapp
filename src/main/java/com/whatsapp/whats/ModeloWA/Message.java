package com.whatsapp.whats.ModeloWA;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String from;
    private Text text;
}
