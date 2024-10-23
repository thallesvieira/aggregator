package com.api.aggregator.resource.gateway.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.gateway.IContactGateway;
import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.model.contact.Contacts;
import com.api.aggregator.resource.gateway.IContactGatewayFeign;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Call feign method that get contacts
     * Convert to page and return
     * @return Page of contacts
     */
    @Override
    public Page<Contact> getAllContacts(int page) {
        try {
            logger.info("Trying get contacts in the page {}", page);
            Response response = contactGatewayFeign.getAllContacts(token, page);

            int totalElementsPerPage = Integer.parseInt(response.headers().get("Page-Items").iterator().next());
            int totalElements = Integer.parseInt(response.headers().get("Total-Count").iterator().next());

            logger.info("Trying convert response to object");
            Contacts contacts = objectMapper.readValue(response.body().asInputStream(), Contacts.class);

            logger.info("Convert result to page");
            Pageable pageable = PageRequest.of(page-1, totalElementsPerPage);
            Page<Contact> contactPage = new PageImpl<>(contacts.getContacts(), pageable, totalElements);

            return contactPage;
        } catch (Exception ex) {
            logger.error("Error to get contacts in the page {}", page);
            throw new ExceptionResponse("Unable to get contacts", HttpStatus.BAD_REQUEST);
        }
    }
}
