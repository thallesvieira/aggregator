package com.api.aggregator.domain.model.contact;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is a singleton to set the source.
 * The goal is to use this global source to identify the API that is requesting
 */
@Getter
@Setter
public class ContactGlobalSource {
    private ContactSource contactSource;

    private static ContactGlobalSource instance;

    private ContactGlobalSource() {
        this.contactSource = ContactSource.KENECT_LABS;
    }

    public static ContactGlobalSource getInstance() {
        if (instance == null) {
            instance = new ContactGlobalSource();
        }
        return instance;
    }
}
