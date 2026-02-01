package com.smart.services;

import com.smart.entitits.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService
{
    Contact saveContact(Contact contact);
    Contact updateContact(Contact contact);
    void deleteContact(int id);
    List<Contact> getAllContacts();
    Contact getContactById(int id);
    List<Contact> getAllContactsByUserId(int id);
    Page<Contact> findAllContactsByUserId(int id, Pageable pageable);
}
