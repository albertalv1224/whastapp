package com.whatsapp.whats.modelo;


import lombok.Data;

@Data
public class Mensaje extends MessageF{
    private String messaging_product; 
    private String recipient_type;
    private String to;
    private String type;
    private Template template;
    
    public Mensaje(String to, String type, Template template) {
        this.messaging_product = "whatsapp";
        this.recipient_type = "individual";
        this.to = to;
        this.type = type;
        this.template = template;
    }

}


