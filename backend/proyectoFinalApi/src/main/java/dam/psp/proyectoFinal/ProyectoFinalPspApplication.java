package dam.psp.proyectoFinal;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;

@SpringBootApplication
public class ProyectoFinalPspApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(ProyectoFinalPspApplication.class);
	
	public static void main(String[] args) {
		logger.info("Se ha iniciado la aplicación");
		SpringApplication.run(ProyectoFinalPspApplication.class, args);
	}

}
