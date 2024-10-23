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
     * This method set first page to start recovery the contacts
     * @return List of contacts
     */
    @Override
    public List<Contact> getAllContacts() {
        int page = 1;
        return getAllContactsByPage(page);
    }

    /**
     * Recursive method for calling the contact gateway.
     * This method is recalled until all pages have been passed.
     * @return List of contacts
     */
    private List<Contact> getAllContactsByPage(int page) {
        Page<Contact> contactPage = contactGateway.getAllContacts(page);
        List<Contact> contacts = new ArrayList<>(contactPage.getContent());

        if (contactPage.hasNext())
            contacts.addAll(getAllContactsByPage(page + 1));

        return contacts;
    }
}
