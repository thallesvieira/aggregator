package com.api.aggregator.domain.gateway;

import com.api.aggregator.domain.model.contact.Contact;
import org.springframework.data.domain.Page;

public interface IContactGateway {
    Page<Contact> getAllContacts(int page);
}
