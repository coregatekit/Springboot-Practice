package com.example.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.demo.Controllers.JwtAuthenticationController;
import com.example.demo.Entitys.Book;
import com.example.demo.Entitys.JwtRequest;
import com.example.demo.Repositorys.BookRepository;
import com.example.demo.Service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest  {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private JwtAuthenticationController JwtAuthenticationController;
 
    @Autowired
    private JwtUserDetailsService JwtUserDetailsService;

    @Before
    public void init() {
        
        Book book = new Book( "Book Name", "Mkyoung", new BigDecimal("10.00"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        JwtRequest req = new JwtRequest();
        req.setPassword("111111111");
        req.setUsername("frame");
        JwtUserDetailsService.save(req);
        
    }

    @Test
    public void find_bookId_OK() throws Exception {
        String accessToken = obtainAccessToken("frame", "111111111");
        
        mockMvc.perform(get("/books/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Book Name")))
                .andExpect(jsonPath("$.author", is("Mkyoung")))
                .andExpect(jsonPath("$.price", is(10.00)));

        verify(bookRepository, times(1)).findById(1L);

    }

    // @Test
    // public void save_book_ok() throws Exception {
    //     String accessToken = obtainAccessToken("frame", "111111111");

    //     Book book = new Book("book2","author2",new BigDecimal("10.01"));
    //     when(bookRepository.save(any(Book.class))).thenReturn(book);
    
    //     mockMvc.perform(post("/books")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .header("Authorization", "Bearer " + accessToken)
    //             .content(om.writeValueAsString(book)))
    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.name",is("book2")))
    //             .andExpect(jsonPath("$.author",is("author2")))
    //             .andExpect(jsonPath("$.price", is(10.01)));

    //     verify(bookRepository,times(1)).save(any(Book.class));
    // }

    // @Test
    // public void delete_book_ok() throws Exception {
    //     String accessToken = obtainAccessToken("frame", "111111111");

    //     doNothing().when(bookRepository).deleteById(1L);

    //     mockMvc.perform(delete("/books/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .header("Authorization", "Bearer " + accessToken))
    //             .andExpect(status().isOk());

    //     verify(bookRepository,times(1)).deleteById(1L);
    // }

    private String obtainAccessToken(String username, String password) throws Exception {
        // String employeeString = "{\"username\":\"frame\",\"password\":\"111111111\"}"; 
        JwtRequest req = new JwtRequest();
        req.setPassword(password);
        req.setUsername(username);

        MvcResult result = mockMvc.perform(post("/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }


}
