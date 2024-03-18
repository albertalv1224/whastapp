package com.whatsapp.whats.modelo;

import lombok.Data;

@Data
public class Language {
    private String code;

    public Language() {
        this.code = "en_US";
    }
    
}
