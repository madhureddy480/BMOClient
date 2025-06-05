package com.madhukarnati.clientapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.madhukarnati.clientapp.exception.EmployeeNotFoundException;
import com.madhukarnati.clientapp.exception.ExternalServiceException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class BmoClientApplication {

	private static final Logger log = LoggerFactory.getLogger(BmoClientApplication.class);
	
	private WebClient webClient;
	
	public static void main(String[] args) {
		SpringApplication.run(BmoClientApplication.class, args);
	}
	
	public BmoClientApplication(WebClient webClient) {
		this.webClient = webClient;
	}
	
	/**
	 * As soon as the application is ready, 
	 * this method will trigger few Asynch API requests to the service. 
	 * Asynch functionality is achieved using Flux here.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onAppReady() {
		
		Flux.just("1", "2", "3","_","T","Emp","11","21","31","22","?","32","12")
	    .flatMap(id ->
	        webClient.get()
	            .uri("/employees/{id}", id)
	            .retrieve()
	            .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new EmployeeNotFoundException("Employee Not Found with ID "+id)))
	            .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new ExternalServiceException("Internal Service Error while fetching Employee with ID "+id)))
	            .bodyToMono(String.class)
	            .onErrorResume( e -> {
	            	log.error(e.getMessage());
	            	return Mono.empty();
	            })
	    )
	    .subscribe(emp -> log.info("Fetched: {}" , emp));
	}
}
