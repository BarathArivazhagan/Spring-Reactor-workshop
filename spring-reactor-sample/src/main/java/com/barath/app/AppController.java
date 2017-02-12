package com.barath.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AppController {
	
	private static final Logger logger=LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private PersonRepository personRep;
	
	@PostMapping(value="/person/add",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Person> savePerson(@RequestBody Person person){
		logger.info("Save person "+person.toString());
		return Mono.just(person);
	}
	
	@PostMapping("/person/addAll")
	public Flux<Person> savePerson(@RequestBody Flux<Person> persons){
		
		if( persons !=null){
			persons.doOnEach((person) -> {
				personRep.savePerson(person.get());
				logger.info("Save person "+person.toString());
			});
		}
		
		return persons;
	}
	
	@GetMapping("/person/read/{personId}")
	public Mono<Person> readPerson(@PathVariable Long personId){
		logger.info("Get person "+personId);		
		return Mono.justOrEmpty(personRep.getPerson(personId));
	}
	
	@GetMapping("/person/delete/{personId}")
	public void deletePerson(@PathVariable Long personId){
		logger.info("Delete person with person Id  "+personId);		
		personRep.deletePerson(personId);
	}
	
	@PostMapping(value="/person/update",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Person> updatePerson(@RequestBody Person person){
		logger.info("Update person "+person.toString());
		return Mono.just(personRep.updatePerson(person));
	}
	
	
	@GetMapping("/person/findall")
	public Flux<Person> findAllPersons(){
		logger.info("Finding all the persons  ");		
		Flux<Person> persons= personRep.findAll();
		logger.info("Persons found  "+persons.blockFirst().toString());	
		persons.awaitOnSubscribe().doOnEach( signal -> {
			System.out.println(signal.get().toString());
		});
			
		return persons;
	}
	
	

}
