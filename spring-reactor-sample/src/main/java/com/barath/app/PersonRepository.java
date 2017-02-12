package com.barath.app;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public class PersonRepository {
	
	private static final Logger logger=LoggerFactory.getLogger(PersonRepository.class);
	private Map<Long,Person> persons=new ConcurrentHashMap<Long,Person>();

	
	public void savePerson(Person person){
		if(person !=null){
			persons.put(person.getPersonId(), person);
		}
		
	}
	
	public Person getPerson(long personId){
		Person person=null;
		if(persons.containsKey(personId)){
			person=persons.get(personId);
			if(person !=null){
				logger.info(person.toString());
			}
		}
		return person;
		
	}
	
	private Person getPerson(Person person){
		if(person !=null){
			return getPerson(person.getPersonId());
		}
		return null;
	}
	
	public Person updatePerson(Person person){
		Person updPerson=getPerson(person);
		if(updPerson !=null){
			savePerson(person);
			logger.info("UPDATED PPERSON "+updPerson.toString());
		}
		
		return updPerson;
	}

	public void deletePerson(long personId){
		
		if(persons.containsKey(personId)){
			persons.remove(personId);
		}
	}

	public Flux<Person> findAll() {		
		persons.entrySet().stream().map(Entry::getValue).collect(Collectors.toList()).forEach(System.out::println);
		return Flux.fromIterable(persons.entrySet().stream().map(Entry::getValue).collect(Collectors.toList()));
	}

	public Map<Long, Person> getPersons() {
		return persons;
	}

	public void setPersons(Map<Long, Person> persons) {
		this.persons = persons;
	}
	
	
	
}
