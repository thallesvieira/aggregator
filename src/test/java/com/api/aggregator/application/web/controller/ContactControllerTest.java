package com.api.aggregator.application.web.controller;

import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.model.contact.ContactSource;
import com.api.aggregator.domain.model.contact.Contacts;
import com.api.aggregator.domain.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactServiceImpl contactService;

    @Test
    void getAllContacts() {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setSource(ContactSource.KENECT_LABS.name());
        contact.setEmail("email");
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());

        mockContacts.add(contact);

        when(contactService.getAllContacts()).thenReturn(mockContacts);

        ResponseEntity<?> response = contactController.getAllContacts();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockContacts, response.getBody());
    }

    @Test
    void throwException_When_Try_GetAllContacts() {
        when(contactService.getAllContacts()).thenThrow(new RuntimeException("failed"));

        Exception exception = assertThrows(Exception.class, () -> {
            contactController.getAllContacts();
        });

        assertEquals("failed", exception.getMessage());
    }
}