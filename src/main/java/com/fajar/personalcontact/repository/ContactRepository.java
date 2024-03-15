package com.fajar.personalcontact.repository;

import com.fajar.personalcontact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>, JpaSpecificationExecutor<Contact> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
