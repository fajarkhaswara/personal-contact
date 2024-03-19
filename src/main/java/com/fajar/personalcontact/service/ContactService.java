package com.fajar.personalcontact.service;

import com.fajar.personalcontact.dto.request.ContactDTO;
import com.fajar.personalcontact.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    List<ContactDTO> getAllContacts();

    Contact createContact(Contact contact);

    Contact updateContact(String id, Contact contact);

    void deleteContact(String id);

    Contact getContactById(String id);

    Page<Contact> getContactsPerPage(Pageable pageable, ContactDTO contactDTO);

    List<Contact> getContactsByName(String name);

}
