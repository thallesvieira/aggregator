package com.api.aggregator.application.web.controller;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.contact.Contact;
import com.api.aggregator.domain.service.IContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Controller responsible to deal with contacts endpoints
 */
@RestController
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private IContactService contactService;

    @PreAuthorize("hasAnyRole('USER', 'SERVICE')")
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            logger.info("Trying to get all contacts");
            return ResponseEntity.ok(contactService.getAllContacts());
        } catch (Exception ex) {
            logger.error("Error to get contacts");
            throw ex;
        }
    }
}
