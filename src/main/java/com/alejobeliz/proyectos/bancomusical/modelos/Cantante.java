package com.alejobeliz.proyectos.bancomusical.modelos;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
@Entity
@Table(name = "cantantes")
public class Cantante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    @OneToMany(mappedBy = "cantante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cancion> canciones;


    public void agregarCancion(Cancion cancion){
        canciones.add(cancion);
    }

    public Cantante(String nombre) {
        this.nombre = nombre;
    }
    public Cantante() {
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}

