package com.api.aggregator.domain.service.impl;

import com.api.aggregator.domain.gateway.IContactGateway;
import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.model.contact.ContactSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    @Mock
    private IContactGateway contactGateway;

    @Test
    void getAllContacts_When_has_2_Pages() {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setSource(ContactSource.KENECT_LABS.name());
        contact.setEmail("email");
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());

        mockContacts.add(contact);

        List<Contact> mockContacts2 = new ArrayList<>();

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setName("Name2");
        contact2.setSource(ContactSource.KENECT_LABS.name());
        contact2.setEmail("email2");
        contact2.setCreatedAt(LocalDateTime.now());
        contact2.setUpdatedAt(LocalDateTime.now());

        mockContacts2.add(contact2);

        Pageable pageable = PageRequest.of(0, 1);
        Page<Contact> contactPage = new PageImpl<>(mockContacts, pageable, 2);
        when(contactGateway.getAllContacts(1)).thenReturn(contactPage);

        pageable = PageRequest.of(1, 10);
        contactPage = new PageImpl<>(mockContacts2, pageable, 20);
        when(contactGateway.getAllContacts(2)).thenReturn(contactPage);

        List<Contact> contacts = contactServiceImpl.getAllContacts();

        assertEquals(contacts.get(0).getId(), mockContacts.get(0).getId());
        assertEquals(contacts.get(0).getName(), mockContacts.get(0).getName());
        assertEquals(contacts.get(1).getId(), mockContacts2.get(0).getId());
        assertEquals(contacts.get(1).getName(), mockContacts2.get(0).getName());
    }

    @Test
    void getAllContacts_When_has_1_Pages() {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setSource(ContactSource.KENECT_LABS.name());
        contact.setEmail("email");
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());

        mockContacts.add(contact);

        Pageable pageable = PageRequest.of(0, 1);
        Page<Contact> contactPage = new PageImpl<>(mockContacts, pageable, 1);
        when(contactGateway.getAllContacts(1)).thenReturn(contactPage);

        List<Contact> contacts = contactServiceImpl.getAllContacts();

        assertEquals(contacts.get(0).getId(), mockContacts.get(0).getId());
        assertEquals(contacts.get(0).getName(), mockContacts.get(0).getName());
    }

    @Test
    void getAllContacts_When_has_0_Pages() {
        Page<Contact> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(contactGateway.getAllContacts(1)).thenReturn(emptyPage);

        List<Contact> contacts = contactServiceImpl.getAllContacts();

        assertTrue(contacts.isEmpty());
    }
}