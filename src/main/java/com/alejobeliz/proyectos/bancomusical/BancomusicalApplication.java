package com.alejobeliz.proyectos.bancomusical;

import com.alejobeliz.proyectos.bancomusical.principal.Principal;
import com.alejobeliz.proyectos.bancomusical.repository.CantanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BancomusicalApplication implements CommandLineRunner {


	@Autowired
	private CantanteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BancomusicalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal=new Principal(repository);
		principal.ejecutarAplicacion();
	}



}

