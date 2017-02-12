package com.barath.app;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.barath.app.Person.Gender;

import ch.qos.logback.core.net.SyslogOutputStream;
import reactor.core.publisher.Flux;

public class TestFluxClass {
	
	@Test
	public void testFlux(){
		
		Flux<Person> persons=Flux.fromIterable(getPersons());
		System.out.println(persons.blockFirst().toString());
		
		persons.doOnEach( signal -> {
			System.out.print("Signal "+signal);
			System.out.print("Person "+signal.get().toString());
		});
		
	}
	
	private List<Person> getPersons(){
		
		Person person1=getPerson(1000, "barath", 25, Gender.MALE);
		Person person2=getPerson(1001, "pari", 25, Gender.FEMALE);
		Person person3=getPerson(1002, "kavitha", 30, Gender.FEMALE);
		Person person4=getPerson(1003, "steve", 32, Gender.MALE);
		Person person5=getPerson(1004, "meghna", 05, Gender.FEMALE);
		
		return Arrays.asList(person1,person2,person3,person4,person5);
	}
	
	private Person getPerson(long personId, String personName, int personAge, Gender personGender){
		return new Person(personId, personName, personAge, personGender);
		
	}
	

}
