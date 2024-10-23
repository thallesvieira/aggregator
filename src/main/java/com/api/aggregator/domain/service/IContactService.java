package com.api.aggregator.domain.service;

import com.api.aggregator.domain.model.contact.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> getAllContacts();
}
