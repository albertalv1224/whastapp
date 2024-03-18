package com.whatsapp.whats.controlador;

import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.whats.ModeloWA.Respuestas;
import com.whatsapp.whats.servicio.RespuestasServicio;
import com.whatsapp.whats.servicio.ServicioWa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/webhook")
public class ControladorWa {

    @Value("${verify.token}")
    private String verifyToken;
    private final ServicioWa servicioWa;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RespuestasServicio respuestasServicio;
    private List<Object> listaMensajes = new ArrayList<>();

    public ControladorWa(ServicioWa servicioWa, RespuestasServicio respuestasServicio) {
        this.servicioWa = servicioWa;
        this.respuestasServicio = respuestasServicio;
    }

   

    @GetMapping("/enviarmensaje")
    public void enviarMensaje() throws JsonProcessingException, MalformedURLException, XmlRpcException {
        servicioWa.enviarMensajes();
    }

    @PostMapping
    public ResponseEntity<String> manejarWebhook(@RequestBody Object payload) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(payload);
        Map<String, Object> mapa = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        List<Object> lista = (List<Object>) ((Map<String, Object>) ((List<Object>) mapa.get("entry")).get(0))
                .get("changes");
        Map<String, Object> mapaDos = (Map<String, Object>) lista.get(0);
        Map<String, Object> messagesObjecto = (Map<String, Object>) mapaDos.get("value");
        listaMensajes = (List<Object>) messagesObjecto.get("messages");

        procesarMensajes(listaMensajes);
      
        return ResponseEntity.status(HttpStatus.OK).body("Mensaje recibido");
    }

    @GetMapping
    public ResponseEntity<String> verificarWebhook(@RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String verifyToken,
            @RequestParam("hub.challenge") String challenge) {
        if ("subscribe".equals(mode) && this.verifyToken.equals(verifyToken)) {
            System.out.println("WEBHOOK_VERIFIED");
            return ResponseEntity.status(HttpStatus.OK).body(challenge);
        } else {
            System.out.println("Verification failed");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification failed");
        }
    }

    public void procesarMensajes(List<Object> mensajes) throws JsonProcessingException {
        if (mensajes != null) {
            for (Object mensaje : mensajes) {
                Map<String, Object> mapaMensaje = (Map<String, Object>) mensaje;
                String fromValue = (String) mapaMensaje.get("from");
                Map<String, Object> textObject = (Map<String, Object>) mapaMensaje.get("text");
                Object valor = textObject.get("body");
                if (valor != null) {
                    String input = (String) valor;
                    if (input.equals("menu")) {
                        servicioWa.mostrarMenu(fromValue);
                    } else{
                        Respuestas respuestaEspecifica = respuestasServicio.calcularRespuesta(input);
                        servicioWa.mostrarRespuestaEspecifica(respuestaEspecifica, fromValue);
                    }
                }
            }
        }
    }

    @Scheduled(fixedDelay = 1000) 
    public void procesarRespuestaProgramada() throws JsonProcessingException {
        procesarMensajes(listaMensajes);
    }
}
