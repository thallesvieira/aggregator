package com.api.aggregator.resource.gateway.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.contact.Contact;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    private ObjectMapper objectMapper;

    private ObjectMapper objectMapperLocal = new ObjectMapper();

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field secretField = ContactGatewayImpl.class.getDeclaredField("token");
        secretField.setAccessible(true);
        secretField.set(contactGatewayImpl, "token");
    }

    @Test
    void testGetAllContactsSuccess() throws Exception {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setEmail("email");

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setName("Name2");
        contact2.setEmail("email2");

        mockContacts.add(contact);
        mockContacts.add(contact2);

        Contacts contacts = new Contacts();
        contacts.setContacts(mockContacts);

        Request request = Request.create(
                Request.HttpMethod.GET,
                "http://api.com/contacts",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8
        );

        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Page-Items", Collections.singletonList("2"));
        headers.put("Total-Count", Collections.singletonList("2"));

        String contactsJson = "{\"contacts\": []}";
        ByteArrayInputStream responseBody = new ByteArrayInputStream(contactsJson.getBytes(StandardCharsets.UTF_8));

        Response response = Response.builder()
                .status(200)
                .headers(headers)
                .body(responseBody, 100)
                .request(request)
                .build();

        when(contactGatewayFeign.getAllContacts("token", 1)).thenReturn(response);

        when(objectMapper.readValue(response.body().asInputStream(), Contacts.class)).thenReturn(contacts);

        Page<Contact> contactPage = contactGatewayImpl.getAllContacts(1);

        assertNotNull(contactPage);
        assertEquals(2, contactPage.getTotalElements());
        assertEquals(2, contactPage.getContent().size());
        assertEquals("Name", contactPage.getContent().get(0).getName());
        assertEquals("Name2", contactPage.getContent().get(1).getName());
    }

    @Test
    void testGetAllContactsSuccessWhenIsEmpty() throws Exception {
        List<Contact> mockContacts = new ArrayList<>();

        Contacts contacts = new Contacts();
        contacts.setContacts(mockContacts);

        Request request = Request.create(
                Request.HttpMethod.GET,
                "http://api.com/contacts",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8
        );

        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Page-Items", Collections.singletonList("1"));
        headers.put("Total-Count", Collections.singletonList("0"));

        String contactsJson = "{\"contacts\": []}";
        ByteArrayInputStream responseBody = new ByteArrayInputStream(contactsJson.getBytes(StandardCharsets.UTF_8));

        Response response = Response.builder()
                .status(200)
                .headers(headers)
                .body(responseBody, 100)
                .request(request)
                .build();

        when(contactGatewayFeign.getAllContacts("token", 1)).thenReturn(response);

        when(objectMapper.readValue(response.body().asInputStream(), Contacts.class)).thenReturn(contacts);

        Page<Contact> contactPage = contactGatewayImpl.getAllContacts(1);

        assertTrue(contactPage.isEmpty());
        assertEquals(0, contactPage.getTotalElements());
        assertEquals(0, contactPage.getContent().size());
    }

    @Test
    void testGetAllContactsThrowsException() {
        when(contactGatewayFeign.getAllContacts("token", 1)).thenThrow(new RuntimeException("Service Unavailable"));

        ExceptionResponse exception = assertThrows(ExceptionResponse.class, () -> {
            contactGatewayImpl.getAllContacts(1);
        });

        assertEquals("Unable to get contacts", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}