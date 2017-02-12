package com.barath.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.barath.app.Person.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringReactorSampleApplicationTests {
	
	private static final Logger logger=LoggerFactory.getLogger(SpringReactorSampleApplicationTests.class);
	private ObjectMapper mapper=new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PersonRepository personRep;
	
	@Before
	public void initMethod(){
		logger.info("Initializing the set up and loading the persons");
		getPersons().stream().forEach(personRep::savePerson);			
	}
	
	
	@Test	
	public void testSavePerson() throws Exception {
		Person person=getPerson(1000, "barath", 25, Gender.MALE);
		String request=mapper.writeValueAsString(person);
		Object response=mockMvc.perform(post("/person/add").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(request)).andExpect(status().isOk())
				.andDo(print())
				.andReturn().getAsyncResult();
		AssertionErrors.assertEquals("SAVE Person test",response, person);
	}
	
	@Test
	public void testReadPerson() throws UnsupportedEncodingException, Exception {
		
		MvcResult mvcResult=mockMvc.perform(get("/person/read/{personId}",1000))
		.andExpect(status().isOk())
		.andDo(print()).andReturn();		
		mockMvc.perform(asyncDispatch(mvcResult))
			.andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.personName",is("barath")));			
	}
	
	@Test
	public void testUpdatePerson() throws Exception{
		Person person=getPerson(1000, "barath", 25, Gender.MALE);
		//updating the person age from 25 to 26
		person.setPersonAge(26);
		String request=mapper.writeValueAsString(person);
		MvcResult result=mockMvc.perform(post("/person/update").content(request).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))			
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn();
		
		mockMvc.perform(asyncDispatch(result))			
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(MockMvcResultMatchers.jsonPath("$.personAge").value(26));
	}
	
	@Test	
	public void testDeletePerson() throws Exception {		
		mockMvc.perform(get("/person/delete/{personId}",1004))
			.andExpect(status().isOk())
			.andDo(print());
		assertEquals(personRep.getPersons().size(), getPersons().size()-1);
			
	}
	
	@Test	
	public void testFindAllPersons() throws Exception {		
		MvcResult result=mockMvc.perform(get("/person/findall"))
			.andExpect(status().isOk())
			.andDo(print()).andReturn();
		mockMvc.perform(asyncDispatch(result))			
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().json(mapper.writeValueAsString(getPersons())));
		
			
	}
	
	
	private Person getPerson(long personId, String personName, int personAge, Gender personGender){
		return new Person(personId, personName, personAge, personGender);
		
	}
	
	
	
	
	
	
	
	private List<Person> getPersons(){
		
		Person person1=getPerson(1000, "barath", 25, Gender.MALE);
		Person person2=getPerson(1001, "pari", 25, Gender.FEMALE);
		Person person3=getPerson(1002, "kavitha", 30, Gender.FEMALE);
		Person person4=getPerson(1003, "steve", 32, Gender.MALE);
		Person person5=getPerson(1004, "meghna", 05, Gender.FEMALE);
		
		return Arrays.asList(person1,person2,person3,person4,person5);
	}

}
