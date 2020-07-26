package com.examen.shiller;

import com.examen.shiller.model.Identification;
import com.examen.shiller.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class ShillerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void personConstructors(){
		Person person=new Person();
		Person person1=new Person("Xavier",33," ");
		Person person2=new Person("Xavier",33," ","SSSSS",new Timestamp(new Date().getTime()),true);
	}

	@Test
	void identificationConstructors(){
		Identification identification=new Identification();
		Identification identification1=new Identification("IDENTIFICATION","NONE");
		Identification identification2=new Identification("IDENTIFICATION","NONE",new Timestamp(new Date().getTime()),true);
	}

}
