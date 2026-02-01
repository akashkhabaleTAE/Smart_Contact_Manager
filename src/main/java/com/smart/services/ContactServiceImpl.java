package com.smart.services;

import com.smart.entitits.Contact;
import com.smart.exceptions.ResourceNotFoundException;
import com.smart.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService
{
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return this.contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact)
    {
        final int id = contact.getId();
        final Contact existingContact = this.contactRepository
                .findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact with contact id: " + id + " not found! ...(in contact service)"));
        existingContact.setUser(contact.getUser());
        existingContact.setImage(contact.getImage());
        existingContact.setAbout(contact.getAbout());
        existingContact.setEmail(contact.getEmail());
        existingContact.setFirstName(contact.getFirstName());
        existingContact.setLastName(contact.getLastName());
        existingContact.setPhone(contact.getPhone());
        existingContact.setWork(contact.getWork());
        final Contact updatedContact = this.contactRepository.save(existingContact);
        return this.contactRepository.save(updatedContact);
    }

    @Override
    public void deleteContact(int id) {
        this.contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getAllContacts() {
        return this.contactRepository.findAll();
    }

    @Override
    public Contact getContactById(int id) {
        return this.contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with contact id: " + id + " not found! ...(in contactService.getContactById())"));
    }

    @Override
    public List<Contact> getAllContactsByUserId(int id) {
        return this.contactRepository.findByUserId(id);
    }

    @Override
    public Page<Contact> findAllContactsByUserId(int id, Pageable pageable) {
        return this.contactRepository.findAllContactsByUserId(id, pageable);
    }


}
