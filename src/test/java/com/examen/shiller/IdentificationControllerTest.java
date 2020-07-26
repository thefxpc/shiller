package com.examen.shiller;

import com.examen.shiller.httpRequest.AddIdentificationRequest;
import com.examen.shiller.httpRequest.ModifyIdentificationRequest;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class IdentificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String PATH="/api/identification";

    @Test
     void getIdentifications() throws Exception {

        mockMvc.
                perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))));
    }

    @Test
     void createIdentification() throws Exception {

        AddIdentificationRequest addIdentificationRequest=new AddIdentificationRequest();
        addIdentificationRequest.setPerson_id(1L);
        addIdentificationRequest.setIdentification_id(1L);
        addIdentificationRequest.setIdentificationNumber("JUNIT");

        String json = mapper.writeValueAsString(addIdentificationRequest);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).andReturn().getResponse();
    }

    @Test
    void createIdentificationPersonNotFound() throws Exception {

        AddIdentificationRequest addIdentificationRequest=new AddIdentificationRequest();
        addIdentificationRequest.setPerson_id(50000L);
        addIdentificationRequest.setIdentification_id(1L);
        addIdentificationRequest.setIdentificationNumber("JUNIT");

        String json = mapper.writeValueAsString(addIdentificationRequest);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void createIdentificationIdentificationNotFound() throws Exception {

        AddIdentificationRequest addIdentificationRequest=new AddIdentificationRequest();
        addIdentificationRequest.setPerson_id(1L);
        addIdentificationRequest.setIdentification_id(4000L);
        addIdentificationRequest.setIdentificationNumber("JUNIT");

        String json = mapper.writeValueAsString(addIdentificationRequest);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void testValidation() throws Exception {

        AddIdentificationRequest addIdentificationRequest=new AddIdentificationRequest();
        addIdentificationRequest.setPerson_id(1L);
        addIdentificationRequest.setIdentification_id(1L);
        addIdentificationRequest.setIdentificationNumber("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");

        String json = mapper.writeValueAsString(addIdentificationRequest);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
    }

    @Test
     void findIdentification() throws Exception {
        mockMvc.perform(get(PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personIdentificationId", Matchers.equalTo(1)));
    }

    @Test
    void testPersonNotFoundExceptionGet() throws Exception {
        mockMvc.perform(get(PATH+"/5000"))
                .andExpect(status().isNotFound());
    }

    @Test
     void updateIdentification() throws Exception {
        ModifyIdentificationRequest modifyIdentificationRequest=new ModifyIdentificationRequest();
        modifyIdentificationRequest.setPersonIdentificationId(1L);
        modifyIdentificationRequest.setIdentificationNumber("JUNIT V5");

        String json = mapper.writeValueAsString(modifyIdentificationRequest);

        mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated());
    }

    @Test
    void updateIdentificationNotFound() throws Exception {
        ModifyIdentificationRequest modifyIdentificationRequest=new ModifyIdentificationRequest();
        modifyIdentificationRequest.setPersonIdentificationId(5000L);
        modifyIdentificationRequest.setIdentificationNumber("JUNIT V5");

        String json = mapper.writeValueAsString(modifyIdentificationRequest);

        mockMvc.perform(put(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
     void deleteIdentification() throws Exception {

        AddIdentificationRequest addIdentificationRequest=new AddIdentificationRequest();
        addIdentificationRequest.setPerson_id(1L);
        addIdentificationRequest.setIdentification_id(1L);
        addIdentificationRequest.setIdentificationNumber("JUNIT");

        String json = mapper.writeValueAsString(addIdentificationRequest);

        MockHttpServletResponse response = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).andReturn().getResponse();

        String resource_url=response.getHeader("Location");
        assert resource_url != null;
        int personIdentificationId = Integer.parseInt(resource_url.replace("http://localhost/api/identification/", ""));

        mockMvc.perform(delete(PATH+"/"+personIdentificationId))
                .andExpect(status().isOk());

    }

    @Test
    void deleteIdentificationNotFound() throws Exception {
        mockMvc.perform(delete(PATH+"/50000"))
                .andExpect(status().isNotFound());
    }
}
