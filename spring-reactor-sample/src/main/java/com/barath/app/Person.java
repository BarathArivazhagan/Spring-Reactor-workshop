package com.barath.app;

import java.io.Serializable;

public class Person implements Serializable{
	
	
	private static final long serialVersionUID = 8411464104125064382L;
	private long personId;
	private String personName;
	private int personAge;
	private Gender personGender;
	
	public enum Gender{
		MALE,FEMALE
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getPersonAge() {
		return personAge;
	}

	public void setPersonAge(int personAge) {
		this.personAge = personAge;
	}

	public Gender getPersonGender() {
		return personGender;
	}

	public void setPersonGender(Gender personGender) {
		this.personGender = personGender;
	}

	public Person(long personId, String personName, int personAge, Gender personGender) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.personAge = personAge;
		this.personGender = personGender;
	}

	public Person() {
		super();
		
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", personName=" + personName + ", personAge=" + personAge
				+ ", personGender=" + personGender + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + personAge;
		result = prime * result + ((personGender == null) ? 0 : personGender.hashCode());
		result = prime * result + (int) (personId ^ (personId >>> 32));
		result = prime * result + ((personName == null) ? 0 : personName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (personAge != other.personAge)
			return false;
		if (personGender != other.personGender)
			return false;
		if (personId != other.personId)
			return false;
		if (personName == null) {
			if (other.personName != null)
				return false;
		} else if (!personName.equals(other.personName))
			return false;
		return true;
	}
	
	
	

}
