package com.alejobeliz.proyectos.bancomusical.modelos;

import jakarta.persistence.*;

import java.sql.Time;
@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String duracion;
    private String genero;
    @ManyToOne
    private Cantante cantante;

    public Cancion(String nombre, String duracion, String genero, Cantante cantante) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.genero = genero;
        this.cantante = cantante;
    }

    public Cancion() {
    }

    @Override
    public String toString() {
        return "Titulo: "+nombre+"\n"+
                "Duración: "+duracion+"\n"+
                "Genero: "+genero+"\n"+
                "Cantante: "+cantante+"\n";
    }
}


