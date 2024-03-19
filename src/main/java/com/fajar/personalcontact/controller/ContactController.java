package com.fajar.personalcontact.controller;

import com.fajar.personalcontact.dto.request.ContactDTO;
import com.fajar.personalcontact.dto.response.ApiResponse;
import com.fajar.personalcontact.dto.response.PageResponseWrapper;
import com.fajar.personalcontact.entity.Contact;
import com.fajar.personalcontact.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ModelMapper modelMapper;

    private final ContactService service;

    public ContactController(ModelMapper modelMapper, ContactService service) {
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @GetMapping
    private List<ContactDTO> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable(name = "id")String id) {
        Contact contact = service.getContactById(id);
        ContactDTO response = modelMapper.map(contact, ContactDTO.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contactRequest = modelMapper.map(contactDTO, Contact.class);
        Contact contact = service.createContact(contactRequest);
        ContactDTO response = modelMapper.map(contact, ContactDTO.class);
        return new ResponseEntity<ContactDTO>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable String id, @RequestBody ContactDTO contactDTO) {
        Contact contactRequest = modelMapper.map(contactDTO, Contact.class);
        Contact contact = service.updateContact(id, contactRequest);

        ContactDTO response = modelMapper.map(contact, ContactDTO.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable(name = "id") String id) {
    service.deleteContact(id);
    ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Contact deleted succesfully", HttpStatus.OK);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/page")
    public PageResponseWrapper<Contact> getAllContacts(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @ModelAttribute ContactDTO contactDTO
            ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Contact> resultPage = service.getContactsPerPage(pageable, contactDTO);
        return new PageResponseWrapper<>(resultPage);
    }

    @GetMapping("/name")
    public List<Contact> getContactsByName(@RequestParam("name") String name) {
        return service.getContactsByName(name);
    }

}
