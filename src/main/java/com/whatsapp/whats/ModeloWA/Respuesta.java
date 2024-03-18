package com.whatsapp.whats.ModeloWA;

import com.whatsapp.whats.modelo.MessageF;

import lombok.Data;

@Data
public class Respuesta extends MessageF{
    private String messaging_product; 
    private String recipient_type;
    private String to;
    private String type;
    private Text text;
    
    public Respuesta(String to, String type, Text text) {
        this.messaging_product = "whatsapp";
        this.recipient_type = "individual";
        this.to = to;
        this.type = type;
        this.text = text;
    }

}

