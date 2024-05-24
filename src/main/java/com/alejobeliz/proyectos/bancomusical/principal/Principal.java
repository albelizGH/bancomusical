package com.alejobeliz.proyectos.bancomusical.principal;

import com.alejobeliz.proyectos.bancomusical.modelos.Cancion;
import com.alejobeliz.proyectos.bancomusical.modelos.Cantante;
import com.alejobeliz.proyectos.bancomusical.repository.CantanteRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private CantanteRepository repositorioCantantes;
    private boolean salir = true;

    public Principal(CantanteRepository repositorioCantantes) {
        this.repositorioCantantes = repositorioCantantes;
    }

    public void ejecutarAplicacion() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    registrarDatosCantante();
                    break;
                case 2:
                    registrarDatosCancion();
                    break;
                case 3:
                    buscarCancionesPorCantantes();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        }
    }

    private void registrarDatosCantante() {
        System.out.print("Ingrese nombre del cantante: ");
        String nombre = teclado.nextLine();
        if (!nombre.isEmpty()) {
            try {
                Cantante cantante = new Cantante(nombre);
                repositorioCantantes.save(cantante);
            }catch(DataIntegrityViolationException e){
                System.out.println("Error: el cantante ya existe en la base de datos\n");
            }
        } else {
            System.out.println("Error: no se ingresó ningún nombre\n");
        }
    }

    private void registrarDatosCancion() {
        System.out.print("Nombre de la cancion: ");
        String nombre = teclado.nextLine();
        if (verificarVacios(nombre)) {
            System.out.println("Error: no se ingresó ningún nombre\n");
            return;
        }

        System.out.print("\nDuracion [mm:ss]: ");
        String duracion = teclado.nextLine();
        String regex = "^([0-5]?[0-9]):([0-5][0-9])$";
        Pattern patron=Pattern.compile(regex);
        Matcher matcher=patron.matcher(duracion);

        if (verificarVacios(duracion) || !matcher.matches()) {
            System.out.println("Ingrese una duración correcta [mm:ss]\n");
            return;
        }

        System.out.print("\nGenero: ");
        String genero = teclado.nextLine();
        if (verificarVacios(genero)) {
            System.out.println("Error: no se ingresó ningún genero\n");
            return;
        }

        System.out.println("\nElija uno de los cantantes disponibles: ");
        listarCantantes();
        String cantanteIngresado = teclado.nextLine();

        if (verificarVacios(cantanteIngresado)) {
            System.out.println("Error: no se ingresó ningún cantante\n");
            return;
        }

        Optional<Cantante> cantante = repositorioCantantes.obtenerCantante(cantanteIngresado);
        if (cantante.isPresent()) {
            Cantante cantanteEncontrado = cantante.get();
            Cancion cancion = new Cancion(nombre, duracion, genero, cantanteEncontrado);
            cantanteEncontrado.agregarCancion(cancion);
            repositorioCantantes.save(cantanteEncontrado);
        } else {
            System.out.println("Error: no se encontró ese cantante\n");
        }
    }

    private void buscarCancionesPorCantantes() {
        System.out.print("Ingrese nombre del cantante: ");
        String cantante = teclado.nextLine();
        if (verificarVacios(cantante)) {
            System.out.println("Error: no se ingresó ningún nombre\n");
            return;
        }
        System.out.println("");
        List<Cancion> canciones = repositorioCantantes.cancionesPorCantante(cantante);
        canciones.forEach(System.out::println);
    }


    private boolean verificarVacios(String texto) {
        return texto.isEmpty();
    }

    private void listarCantantes() {
        List<Cantante> cantantesRepositorio = repositorioCantantes.listarCantantes();
        cantantesRepositorio.forEach(cantante -> System.out.println(cantante.getNombre()));
    }

    private void mostrarMenu() {
        System.out.println("""
                Elige una opción:
                1-Registrar datos de cantante
                2-Registrar datos de canciones
                3-Buscar canciones por cantantes
                
                0-Salir
                """);
    }
}
