package com.fajar.personalcontact.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private String contactName;
    private String ContactPhoneNumber;
    private String contactEmail;
    private String contactAddress;
}
