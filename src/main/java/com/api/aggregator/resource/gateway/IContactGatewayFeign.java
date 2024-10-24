package com.api.aggregator.resource.gateway;

import com.api.aggregator.domain.model.contact.Contacts;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface feign configured to get contacts.
 * Get url from environment and configure method with parameters
 */
@FeignClient(name = "getContacts", url = "${api.contact.url}")
public interface IContactGatewayFeign {

    @GetMapping("/contacts")
    ResponseEntity<Contacts> getAllContacts(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("page") int page
    );
}
