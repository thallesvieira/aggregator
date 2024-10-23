package com.api.aggregator.resource.gateway.impl;

import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.model.contact.ContactSource;
import com.api.aggregator.domain.model.contact.Contacts;
import com.api.aggregator.resource.gateway.IContactGatewayFeign;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "api.contact.token=token"
})
@ExtendWith(MockitoExtension.class)
class ContactGatewayImplTest {

    @InjectMocks
    private ContactGatewayImpl contactGatewayImpl;

    @Mock
    private IContactGatewayFeign contactGatewayFeign;

    @Mock
    private ObjectMapper objectMapperMock;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field secretField = ContactGatewayImpl.class.getDeclaredField("token");
        secretField.setAccessible(true);
        secretField.set(contactGatewayImpl, "token");
//        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllContactsSuccess() throws Exception {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setSource(ContactSource.KENECT_LABS.name());
        contact.setEmail("email");
//        contact.setCreatedAt(LocalDateTime.now());
//        contact.setUpdatedAt(LocalDateTime.now());

        mockContacts.add(contact);

        Contacts contacts = new Contacts();
        contacts.setContacts(mockContacts);

        Request request = Request.create(
                Request.HttpMethod.GET,
                "http://api.example.com/contacts",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8
        );

        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Page-Items", Collections.singletonList("1"));
        headers.put("Total-Count", Collections.singletonList("1"));

        String jsonResponse = objectMapper.writeValueAsString(contacts);
        Response response = Response.builder()
                .status(200)
                .headers(headers)
                .body(Arrays.toString(jsonResponse.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
                .request(request)
                .build();
        when(contactGatewayFeign.getAllContacts("token", 1)).thenReturn(response);

        //when(objectMapperMock.readValue(response.body().asInputStream(), Contacts.class)).thenReturn(contacts);

        Page<Contact> contactPage = contactGatewayImpl.getAllContacts(1);

        assertNotNull(contactPage);
        assertEquals(1, contactPage.getTotalElements());
        assertEquals(1, contactPage.getContent().size());
        assertEquals("Name", contactPage.getContent().get(0).getName());
    }

//    @Test
//    void testGetAllContactsThrowsException() {
//        // Mockando a exceção
//        when(contactGatewayFeignMock.getAllContacts(anyString(), anyInt())).thenThrow(new RuntimeException("Service Unavailable"));
//
//        // Call the method and assert exception
//        ExceptionResponse exception = assertThrows(ExceptionResponse.class, () -> {
//            contactGateway.getAllContacts(1);
//        });
//
//        assertEquals("Unable to get contacts", exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    // Adicione mais testes conforme necessário
//}

}