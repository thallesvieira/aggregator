package com.api.aggregator.resource.gateway.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.gateway.IContactGateway;
import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.model.contact.ContactGlobalSource;
import com.api.aggregator.domain.model.contact.ContactSource;
import com.api.aggregator.domain.model.contact.Contacts;
import com.api.aggregator.resource.gateway.IContactGatewayFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation from gateway interface feign to get all contacts
 */
@Service
public class ContactGatewayImpl implements IContactGateway {
    private static final Logger logger = LoggerFactory.getLogger(ContactGatewayImpl.class);

    @Value("${api.contact.token}")
    private String token;

    @Autowired
    private IContactGatewayFeign contactGatewayFeign;

    /**
     * Call feign method that get contacts
     * Convert to page and return
     * @return Page of contacts
     */
    @Override
    public Page<Contact> getAllContacts(int page) {
        try {
            logger.info("Set global source {}", ContactSource.KENECT_LABS.name());
            ContactGlobalSource.getInstance().setContactSource(ContactSource.KENECT_LABS);

            logger.info("Trying get contacts in the page {}", page);
            ResponseEntity<Contacts> response = contactGatewayFeign.getAllContacts(token, page);

            int totalElementsPerPage = 0;
            int totalElements = 0;

            if (response.getHeaders().containsKey("page-items"))
                totalElementsPerPage = Integer.parseInt(response.getHeaders().get("page-items").iterator().next());

            if (response.getHeaders().containsKey("total-count"))
                totalElements = Integer.parseInt(response.getHeaders().get("total-count").iterator().next());

            logger.info("Convert result to page");
            Pageable pageable = PageRequest.of(page-1, totalElementsPerPage);
            Page<Contact> contactPage = new PageImpl<>(response.getBody().getContacts(), pageable, totalElements);

            return contactPage;
        } catch (Exception ex) {
            logger.error("Error to get contacts in the page {}", page);
            throw new ExceptionResponse("Unable to get contacts", HttpStatus.BAD_REQUEST);
        }
    }
}
