package com.examen.shiller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.httpRequest.ModifyPersonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String PATH="/api/person";

    @Test
    void getPerson() throws Exception {

        mockMvc.
                perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))));
    }

    @Test
    void createPerson() throws Exception {
        AddPersonRequest addPersonRequest=new AddPersonRequest();
        addPersonRequest.setName("Xavier JUNIT Perez");
        addPersonRequest.setAge(33);
        addPersonRequest.setGender("H");

        String json = mapper.writeValueAsString(addPersonRequest);

        mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).andReturn().getResponse();
    }

    @Test
    void testValidation() throws Exception {
        AddPersonRequest addPersonRequest=new AddPersonRequest();
        addPersonRequest.setName(null);
        addPersonRequest.setAge(5000);
        addPersonRequest.setGender("HELLO THERE");

        String json = mapper.writeValueAsString(addPersonRequest);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
    }

    @Test
    void findPerson() throws Exception {
        mockMvc.perform(get(PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId", Matchers.equalTo(1)));
    }

    @Test
    void testNotFoundException() throws Exception {
        mockMvc.perform(get(PATH+"/50000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePersona() throws Exception {

        ModifyPersonRequest modifyPersonRequest=new ModifyPersonRequest();
        modifyPersonRequest.setPerson_id(1L);
        modifyPersonRequest.setName("JUNIT");
        modifyPersonRequest.setAge(99);
        modifyPersonRequest.setGender("M");

        String json = mapper.writeValueAsString(modifyPersonRequest);

        mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated());
    }

    @Test
    void updatePersonaNotFound() throws Exception {

        ModifyPersonRequest modifyPersonRequest=new ModifyPersonRequest();
        modifyPersonRequest.setPerson_id(5000L);
        modifyPersonRequest.setName("JUNIT");
        modifyPersonRequest.setAge(99);
        modifyPersonRequest.setGender("M");

        String json = mapper.writeValueAsString(modifyPersonRequest);

        mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void deletePersona() throws Exception {
        AddPersonRequest addPersonRequest=new AddPersonRequest();
        addPersonRequest.setName("Xavier JUNIT Perez");
        addPersonRequest.setAge(33);
        addPersonRequest.setGender("H");

        String json = mapper.writeValueAsString(addPersonRequest);

        MockHttpServletResponse response = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).andReturn().getResponse();

        String resource_url=response.getHeader("Location");
        assert resource_url != null;
        int person_id = Integer.parseInt(resource_url.replace("http://localhost/api/person/", ""));

        mockMvc.perform(delete(PATH+"/"+person_id))
                .andExpect(status().isOk());

    }

    @Test
    void deletePersonaNotFound() throws Exception {
        mockMvc.perform(delete(PATH+"/500000"))
                .andExpect(status().isNotFound());
    }

}