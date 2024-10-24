package com.api.aggregator.domain.model.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Contact {
    private Long id;
    private String name;
    private String email;
    private String source = ContactGlobalSource.getInstance().getContactSource().name();
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
