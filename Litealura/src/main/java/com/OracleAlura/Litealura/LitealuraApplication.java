package com.OracleAlura.Litealura;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import com.OracleAlura.Litealura.principal.Principal;
import com.OracleAlura.Litealura.repository.ILibroRepository;
import com.OracleAlura.Litealura.repository.IAutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

@SpringBootApplication
public class LitealuraApplication implements CommandLineRunner {
	@Autowired
	private ILibroRepository repository;
	@Autowired
	private IAutorRepository repositoryAutor;
	public static void main(String[] args) {
		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.opcionesMenu();

		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t : threadSet) {
			System.out.println(t);
		}
	}
}