package com.smart.repositories;

import com.smart.entitits.Contact;
import com.smart.entitits.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Page<Contact> findAllContactsByUserId(int id, Pageable pageable);
    List<Contact> findByUserId(int id);
    List<Contact> findByUser(User user);
}
