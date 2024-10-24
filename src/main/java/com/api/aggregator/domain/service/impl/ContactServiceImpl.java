package com.api.aggregator.domain.service.impl;

import com.api.aggregator.domain.gateway.IContactGateway;
import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.service.IContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to deal with contacts
 */
@Service
public class ContactServiceImpl implements IContactService {
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private IContactGateway contactGateway;

    /**
     * This method is responsible to recovery all the contacts.
     * Called the first page and after continues getting the pages as long as they exist
     * @return List of contacts
     */
    @Override
    public List<Contact> getAllContacts() {
        int page = 1;

        Page<Contact> contactPage = contactGateway.getAllContacts(page);
        List<Contact> contacts = new ArrayList<>(contactPage.getContent());

        while (contactPage.hasNext()) {
            logger.info("Getting new contacts from page {}", page+1);
            contactPage = contactGateway.getAllContacts(page+1);
            contacts.addAll(contactPage.getContent());
        }

        return contacts;
    }
}
