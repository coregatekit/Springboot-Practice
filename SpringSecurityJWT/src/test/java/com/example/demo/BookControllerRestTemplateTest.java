// package com.example.demo;

// import com.example.demo.Entitys.Book;
// import com.example.demo.Repositorys.BookRepository;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.web.server.LocalServerPort;
// import org.springframework.http.*;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;

// import java.math.BigDecimal;
// import java.net.URI;
// import java.net.URISyntaxException;

// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
// @ActiveProfiles("test")
// public class BookControllerRestTemplateTest {

//     private static final ObjectMapper om = new ObjectMapper();

//     @LocalServerPort
//     int randomServerPort;

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @MockBean
//     private BookRepository mockRepository;

//     // @Test
//     // public void testAddBooksuccess() throws URISyntaxException {
//     //     final String baseUrl = "http://localhost:"+randomServerPort+"/books/";
//     //     URI uri = new URI(baseUrl);
//     //     Book book = new Book("book1", "Gilly", new BigDecimal("15.41"));
//     //     HttpHeaders headers = new HttpHeaders();
//     //     headers.setContentType(MediaType.APPLICATION_JSON);
//     //     HttpEntity<Book> request = new HttpEntity<>(book, headers);
        
//     //     ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
//     //     printJSON(result);
//     //     //Verify request succeed
//     //     Assert.assertEquals(201, result.getStatusCodeValue());
//     // }

//     // @Test
//     // public void testAddBookNameIsMissing() throws URISyntaxException 
//     // {
//     //     final String baseUrl = "http://localhost:"+randomServerPort+"/books/";
//     //     URI uri = new URI(baseUrl);
//     //     Book book = new Book();
//     //     book.setAuthor("frame");
//     //     book.setName(null);
//     //     book.setPrice(new BigDecimal("15.41"));
         
//     //     HttpHeaders headers = new HttpHeaders();
//     //     headers.setContentType(MediaType.APPLICATION_JSON);

//     //     HttpEntity<Book> request = new HttpEntity<>(book, headers);
         
//     //     ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
//     //     printJSON(result);
//     //     //Verify bad request missing name
//     //     Assert.assertEquals(400, result.getStatusCodeValue());
//     //     Assert.assertEquals(true, result.getBody().contains("Please provide a name"));
//     // }


//     private static void printJSON(Object object) {
//         String result;
//         try {
//             result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//             System.out.println(result);
//         } catch (JsonProcessingException e) {
//             e.printStackTrace();
//         }
//     }

// }