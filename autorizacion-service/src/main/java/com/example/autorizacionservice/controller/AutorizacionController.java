package com.example.autorizacionservice.controller;

import com.example.autorizacionservice.entity.AutorizacionEntity;
import com.example.autorizacionservice.model.DatarelojModel;
import com.example.autorizacionservice.repository.AutorizacionRepository;
import com.example.autorizacionservice.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/autorizacion")
public class AutorizacionController {
    
    @Autowired
    AutorizacionService autorizacionService;
    
    @Autowired
    AutorizacionRepository autorizacionRepository;

    @GetMapping
    public ResponseEntity<List<AutorizacionEntity>> listarAutorizaciones(){
        List<AutorizacionEntity> autorizaciones = autorizacionService.listarAutorizaciones();
        return ResponseEntity.ok(autorizaciones);
    }

    // este se va a transformar en calcular horas extras
    @GetMapping("/calcularHorasExtras")
    public ResponseEntity<DatarelojModel[]> getMarcasReloj(){
        DatarelojModel[] marcasReloj = autorizacionService.getMarcasReloj();
        System.out.println(marcasReloj[0].getRutEmpleadoReloj());
        System.out.println("hola");
        autorizacionService.calcularHorasExtras(marcasReloj);
        return ResponseEntity.ok(marcasReloj);
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<AutorizacionEntity> actualizarAutorizaciones(@PathVariable Long id){
        AutorizacionEntity autorizacion = autorizacionRepository.getById(id);
        System.out.println("estado: "+autorizacion.getAutorizado());
        if(autorizacion.getAutorizado()==0){
            autorizacionService.actualizarAutorizacion(1, id);
        }else{
            autorizacionService.actualizarAutorizacion(0, id);
        }
        return ResponseEntity.ok(autorizacion);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<List<AutorizacionEntity>> eliminarAutorizaciones(){
        autorizacionService.eliminarAutorizaciones();
        List<AutorizacionEntity> autorizaciones = autorizacionService.listarAutorizaciones();
        return ResponseEntity.ok(autorizaciones);
    }
}
