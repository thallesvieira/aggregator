package com.api.aggregator.resource.gateway;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
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
    Response getAllContacts(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("page") int page
    );
}
