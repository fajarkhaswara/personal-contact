package com.fajar.personalcontact.util.specification;

import com.fajar.personalcontact.dto.request.ContactDTO;
import com.fajar.personalcontact.entity.Contact;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ContactSpecification {
    public static Specification<Contact> getSpecification(ContactDTO contactDTO) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (contactDTO.getContactName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + contactDTO.getContactName() + "%"));
            }
            if (contactDTO.getContactEmail() != null) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + contactDTO.getContactEmail() + "%"));
            }
            if (contactDTO.getContactAddress() != null) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + contactDTO.getContactAddress() + "%"));
            }
            if (contactDTO.getContactPhoneNumber() != null) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + contactDTO.getContactPhoneNumber() + "%"));
            }
            Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicatesArray);
        });
    }
}

