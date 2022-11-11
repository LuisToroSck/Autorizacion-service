package com.example.autorizacionservice.repository;

import com.example.autorizacionservice.entity.AutorizacionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionEntity, Long> {

    @Transactional
    @Modifying
    // CAMBIÉ "AUTORIZACION" A "AUTORIZACION_ENTITY" PARA QUE FUNCIONARA
    @Query(value = "update autorizacion_entity a set a.autorizado = :aut where a.id=:id",nativeQuery = true)
    void actualizarAutorizacion(@Param("aut") int aut, @Param("id") Long id);

    @Query("select a from AutorizacionEntity a where a.id=:id")
    AutorizacionEntity getById(@Param("id") Long id);
}