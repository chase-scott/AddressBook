package com.example.lab4;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.lab4.model.AddressBook;
import com.example.lab4.model.BuddyInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"buddyBook\":[{\"id\":2,\"name\":\"test1\",\"address\":\"test1\",\"phoneNumber\":\"test1\"}]}]")));
    }

    @Test
    public void viewAddressBookTest() throws Exception {
        this.mockMvc.perform(get("/addressbooks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test1")));
    }

    @Test
    public void addBuddyTest() throws Exception {
        this.mockMvc.perform(put("/1/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new BuddyInfo("test1", "test1", "test1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"buddyBook\":[{\"id\":1,\"name\":\"The Doctor\",\"address\":\"The TARDIS\",\"phoneNumber\":\"07700 900461\"},{\"id\":2,\"name\":\"test1\",\"address\":\"test1\",\"phoneNumber\":\"test1\"}]}"
                )));
    }

    @Test
    public void deleteBuddyTest() throws Exception {
        this.mockMvc.perform(delete("/1/buddies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"buddyBook\":[{\"id\":2,\"name\":\"test1\",\"address\":\"test1\",\"phoneNumber\":\"test1\"}]}"));
    }

    @Test
    public void addAddressBookTest() throws Exception {
        this.mockMvc.perform(post("/")
                .content(asJsonString(new AddressBook()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"buddyBook\":[]}"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}