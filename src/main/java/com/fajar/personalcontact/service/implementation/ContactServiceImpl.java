package com.fajar.personalcontact.service.implementation;

import com.fajar.personalcontact.dto.request.ContactDTO;
import com.fajar.personalcontact.entity.Contact;
import com.fajar.personalcontact.exception.*;
import com.fajar.personalcontact.repository.ContactRepository;
import com.fajar.personalcontact.service.ContactService;
import com.fajar.personalcontact.util.specification.ContactSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.fajar.personalcontact.util.constant.ValidationConstant.EMAIL_PATTERN;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;

    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = repository.findAll();
        if(contacts.isEmpty()) {
            throw new EmptyContactListException("No contacts were found");
        }
        return repository.findAll();
    }

    @Override
    public Contact createContact(Contact contact) {
        String email = contact.getEmail();
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email address");
        }
        if (repository.existsByEmail(contact.getEmail())) {
            throw new DuplicateDataException("Email already exists");
        }
        if (repository.existsByPhoneNumber(contact.getPhoneNumber())) {
            throw new DuplicateDataException("Phone number already exists");
        }
        return repository.save(contact);
    }

    @Override
    @Transactional
    public Contact updateContact(String id, Contact contactRequest) {
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "id", id));
        if (!contact.getEmail().equals(contactRequest.getEmail()) && repository.existsByEmail(contactRequest.getEmail())) {
            throw new DuplicateDataException("Email already exists");
        }
        if (!contact.getPhoneNumber().equals(contactRequest.getPhoneNumber()) && repository.existsByPhoneNumber(contactRequest.getPhoneNumber())) {
            throw new DuplicateDataException("Phone number already exists");
        }
        contact.setName(contactRequest.getName());
        contact.setPhoneNumber(contactRequest.getPhoneNumber());
        contact.setEmail(contactRequest.getEmail());
        contact.setAddress(contactRequest.getAddress());
        return repository.save(contact);
    }

    @Override
    public void deleteContact(String id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Contact", "id", id);
                        }
                );
    }

    @Override
    public Contact getContactById(String id) {
        Optional<Contact> result = repository.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Contact", "id", id);
        }
    }
    @Override
    public Page<Contact> getContactsPerPage(Pageable pageable, ContactDTO contactDTO) {
        Specification<Contact> contactSpecification = ContactSpecification.getSpecification(contactDTO);
        Page<Contact> contacts = repository.findAll(contactSpecification, pageable);
        if (contacts.isEmpty()) {
            throw new NoContactsFoundException();
        }
        return contacts;
    }


    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    @Override
    public List<Contact> getContactsByName(String name) {
        List<Contact> contacts = repository.findByNativeQueryNameContainingIgnoreCase(name.toLowerCase());
        if (contacts.isEmpty()) {
            throw new ResourceNotFoundException("Contact", "name", name);
        }
        return contacts;
    }

}
