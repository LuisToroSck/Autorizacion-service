package com.example.autorizacionservice.service;

import com.example.autorizacionservice.entity.AutorizacionEntity;
import com.example.autorizacionservice.repository.AutorizacionRepository;
import com.example.autorizacionservice.model.DatarelojModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import java.util.List;

@Service
public class AutorizacionService {

    @Autowired
    private AutorizacionRepository autorizacionRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<AutorizacionEntity> listarAutorizaciones(){
        return autorizacionRepository.findAll();
    }

    public AutorizacionEntity guardarAutorizacion(AutorizacionEntity autorizacion){
        return autorizacionRepository.save(autorizacion);
    }

    public void actualizarAutorizacion(int aut, Long id){
        autorizacionRepository.actualizarAutorizacion(aut,id);
    }
    
    public DatarelojModel[] getMarcasReloj(){
        DatarelojModel[] marcasReloj = restTemplate.getForObject("http://datareloj-service/datareloj", DatarelojModel[].class);
        return marcasReloj;
    }

    public void calcularHorasExtras(DatarelojModel[] marcasReloj){
        int j = 0;
        while(j < marcasReloj.length){
            int horasExtras = 0;
            if(marcasReloj[j].getHora().getHours() >= 19) {
                horasExtras = marcasReloj[j].getHora().getHours() - 18;

                AutorizacionEntity nuevaAutorizacion = new AutorizacionEntity();
                nuevaAutorizacion.setRutEmpleado(marcasReloj[j].getRutEmpleadoReloj());
                nuevaAutorizacion.setFecha(marcasReloj[j].getFecha());
                nuevaAutorizacion.setCantidadHorasExtras(horasExtras);
                nuevaAutorizacion.setAutorizado(0);

                guardarAutorizacion(nuevaAutorizacion);
            }
            j = j + 1;
        }
    }

    public void eliminarAutorizaciones(){
        autorizacionRepository.deleteAll();
    }
}