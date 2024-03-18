package com.whatsapp.whats.servicio;

import org.springframework.stereotype.Service;

import com.whatsapp.whats.ModeloWA.Respuestas; 
import lombok.Data;

@Data
@Service
public class RespuestasServicio {

    public Respuestas calcularRespuesta(String input){
        Respuestas respuestas = new Respuestas();
        switch (input) {
            case "1": 
                    respuestas.setRespuestaBot("Con gusto, nuestros productos son los de más alta calidad en el mercado. Pruebalos, no se arrepentirá"); 
                
                break;
            case "2":
                    respuestas.setRespuestaBot("Inmediatamente. Ahí le va el menú, agárrese"); 
                  
                break;
            case "3":
                    respuestas.setRespuestaBot("Espere un momento, en unos instantes será atendido por nuestros ejecutivos"); 
                   
                break;
            default:
                respuestas.setRespuestaBot("Ingrese una opcion correcta y vuelva a intentarlo");
                break;
        }
        return respuestas;
    }
}
