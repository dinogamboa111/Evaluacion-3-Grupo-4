package com.evaluacion3.spaceti_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpacetiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacetiApiApplication.class, args);



			System.out.println("\n" +
				
				"      SpaceTI Backend API \n" +
				"                                                    \n" +
				"     Puerto: 8080\n" +
				"     Base URL: http://localhost:8080/api\n" +
				"      \n" +
				"  	  Endpoints:    \n" +
				"     • GET    /api/productos                          \n" +
				"     • GET    /api/productos/{id}                    \n" +
				"     • POST   /api/productos                          \n" +
				"     • PUT    /api/productos/{id}                     \n" +
				"     • DELETE /api/productos/{id}                     \n" +
				"                                                      \n" +
				"     • POST   /api/contacto                           \n" +
				"     • GET    /api/contacto                           \n" +
				"                                                      \n" +
				"     • POST   /api/ordenes                            \n" +
				"     • GET    /api/ordenes                            \n" +
				"     • GET    /api/ordenes/{id}                       \n" +
				"                                                      \n" +
				"    Base de Datos: PostgreSQL (spaceti_db)          \n");
	}

}
