package com.alejobeliz.proyectos.bancomusical.repository;

import com.alejobeliz.proyectos.bancomusical.modelos.Cancion;
import com.alejobeliz.proyectos.bancomusical.modelos.Cantante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.management.ValueExp;
import java.util.List;
import java.util.Optional;

public interface CantanteRepository extends JpaRepository<Cantante,Long> {

    //@Query(value = "SELECT * FROM cantantes",nativeQuery = true)
    @Query("SELECT c FROM Cantante c")
    List<Cantante> listarCantantes();

    @Query("SELECT c FROM Cantante c WHERE c.nombre = :nombre")
    Optional<Cantante> obtenerCantante(String nombre);

    @Query("SELECT can FROM Cancion can WHERE can.cantante.nombre = :nombre")
    List<Cancion> cancionesPorCantante(String nombre);


}



