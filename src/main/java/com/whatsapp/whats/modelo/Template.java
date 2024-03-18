package com.whatsapp.whats.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Template{
    private String name;
    private Language language; 

}
