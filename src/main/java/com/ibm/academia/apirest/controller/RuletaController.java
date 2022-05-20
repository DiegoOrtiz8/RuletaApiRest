package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Apuesta;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.services.ApuestaDAO;
import com.ibm.academia.apirest.services.RuletaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruleta")
public class RuletaController {

    @Autowired
    private RuletaDAO ruletaDAO;

    @Autowired
    private ApuestaDAO apuestaDAO;

    Logger logger = LoggerFactory.getLogger(RuletaController.class);

    /**
     * Endpoint para crear una nueva ruleta
     * @param ruleta JSON con informacion de la nueva ruleta
     * @param result Lista de errores
     * @return id de la ruleta creada
     * @author DECO 20/05/2022
     */
    @PostMapping
    public ResponseEntity<?> crearRuleta(@Valid @RequestBody Ruleta ruleta, BindingResult result) {

        Map<String, Object> respuesta = new HashMap<String, Object>();
        if (result.hasErrors())
        {
            List<String> listaErrores = result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            respuesta.put("Lista Errores", listaErrores);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        Ruleta ruletaCreada = ruletaDAO.guardar(ruleta);
        respuesta.put("id", ruletaCreada.getId().toString());
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint para abrir ruletas creadas previamente
     * @param ruletaId id de la ruleta a abrir
     * @return resultado de la operacion
     * @author DECO 20/05/2022
     */
    @PostMapping("/abrir")
    public ResponseEntity<?> abrirRuleta(@RequestParam(name = "id") Integer ruletaId) {

        Optional<Ruleta> ruleta = null;

        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            ruleta = ruletaDAO.buscarPorId(ruletaId);
            ruletaDAO.aperturaRuleta(ruleta.get());
            respuesta.put("Operacion", "Exitosa");

            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            respuesta.put("Operacion", "Denegada");
        }

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Endpoint para realizar apuestas
     * @param ruletaId Id de la ruleta ha jugar
     * @param dineroApuesta dinero a apostar
     * @param tipoApuesta tipo de apuesta, ya sea por numero o por color
     * @param apuesta opcion elegida
     * @return Retorna la apuesta creada
     * @author DECO 20/05/2022
     */
    @GetMapping("/apuesta")
    public ResponseEntity<?> apostar(@RequestParam(name = "id") Integer ruletaId,
                                     @RequestParam(name = "dinero") Double dineroApuesta,
                                     @RequestParam(name = "tipoApuesta") Integer tipoApuesta,
                                     @RequestParam(name = "apuesta") String apuesta) {

        Ruleta ruletaEncontrada = null;
        Apuesta apuestaRealizada = null;

        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            ruletaEncontrada = ruletaDAO.buscarPorId(ruletaId).get();
            apuestaRealizada = apuestaDAO.validarApuesta(dineroApuesta, ruletaEncontrada, tipoApuesta, apuesta);

            return new ResponseEntity<Apuesta>(apuestaRealizada, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            respuesta.put("error", "No se pudo realizar la apuesta");
        }

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
    }
}
