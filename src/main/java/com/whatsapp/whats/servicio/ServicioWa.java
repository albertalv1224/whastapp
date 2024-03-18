package com.whatsapp.whats.servicio;

import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.whatsapp.whats.ModeloWA.Respuesta;
import com.whatsapp.whats.ModeloWA.Respuestas;
import com.whatsapp.whats.ModeloWA.Text;
import com.whatsapp.whats.modelo.Language;

import com.whatsapp.whats.modelo.Mensaje;
import com.whatsapp.whats.modelo.MessageF;
import com.whatsapp.whats.modelo.Template;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class ServicioWa {
    @Value("${bearer.token}")
    private String token;


    public ServicioWa(){

    }

   

    public void enviarMensajes() throws JsonProcessingException, MalformedURLException, XmlRpcException {
        List<String> lista = new ArrayList<>();
        lista.add("**NUMERO TELEFONICO**");
        lista.add("**NUMERO TELEFONICO**");
 

        Language language = new Language();
        Template template = new Template("hello_world", language);
        for (String numero : lista) {
        
            Mensaje mensaje = new Mensaje(numero, "template", template);
            enviarMensaje(mensaje);
        }
    }

    public void enviarMensaje(MessageF message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://graph.facebook.com/v18.0/245733418621172/messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<MessageF> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //System.out.println(response);
    }

    public void mostrarMenu(String numero) {
        Text text = new Text(
                "1. Informes sobre nuestros productos\n" +
                        "2. Ver el menú de nuestros productos\n" +
                        "3. Hablar con un ejecutivo\n" +
                        "En qué le podemos ayudar?");
        Respuesta respuesta = new Respuesta(numero, "text", text);
        enviarMensaje(respuesta);
    }

    public void mostrarRespuestaEspecifica(Respuestas respuestas, String numero){
        String texto = respuestas.getRespuestaBot();
        Text text = new Text(texto);
        Respuesta respuesta = new Respuesta(numero, "text", text);
        enviarMensaje(respuesta);
    }
}