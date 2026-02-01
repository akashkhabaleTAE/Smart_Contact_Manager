package com.smart.controllers;

import com.smart.entitits.Contact;
import com.smart.entitits.User;
import com.smart.entitits.UserRole;
import com.smart.helper.Message;
import com.smart.services.ContactService;
import com.smart.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        final String username = principal.getName();
        final User currentUser = userService.getUserByEmail(username);
        System.out.println("User with username: " + username + " logged in.");
        model.addAttribute("user",currentUser);
        model.addAttribute("title", "DASHBOARD - Smart Contact Manager");
        return "user-views/dashboard";
    }

    @GetMapping("/addContact")
    public String addContact(Model model) {
        model.addAttribute("title", "Add Contact - Smart Contact Manager");
        model.addAttribute("contact", new Contact());
        return "user-views/add-contact";
    }

    @PostMapping("/processContact")
    public String processContact(@Valid @ModelAttribute Contact contact, BindingResult result, @RequestParam("profileImage") MultipartFile imageFile, Model model, Principal principal, HttpSession session)
    {
        try {
            session.removeAttribute("message");
            if (result.hasErrors()){
                model.addAttribute("contact", contact);
                return "user-views/add-contact";
            }
            if (imageFile.isEmpty()){
                contact.setImage("default.jpg");
                System.out.println("File is empty...");
            }else {
                String imageName = imageFile.getOriginalFilename();
                contact.setImage(imageName);
                final File images = new ClassPathResource("static/images").getFile();
                final Path path = Paths.get(images.getAbsolutePath() + File.separator + imageName);
                Files.copy(imageFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            final String username = principal.getName();
            final User currentUser = userService.getUserByEmail(username);
            contact.setUser(currentUser);
            currentUser.getContacts().add(contact);
            final User savedUser = this.userService.saveUser(currentUser);
            final List<Contact> userContacts = savedUser.getContacts();
            for (Contact c : userContacts) {
                if (c.getEmail().equals(contact.getEmail())) {
                    Message message = new Message("Contact created successfully.", "alert-success");
                    session.setAttribute("message", message);
                    model.addAttribute("contact", new Contact());
                    return "redirect:/users/addContact";
                }
            }
            model.addAttribute("contact", contact);
            return "redirect:/users/addContact";
        } catch(Exception e) {
            Message message = new Message("Contact creation failed: " + e.getMessage(),"alert-danger");
            session.setAttribute("message",message);
            model.addAttribute("contact",contact);
            return "user-views/add-contact";
        }
    }

    @GetMapping("/viewContacts/{currentPage}")
    public String viewContacts(@PathVariable("currentPage") Integer currentPage, Model model, Principal principal) {
        model.addAttribute("title", "View Contacts - Smart Contact Manager");
        final String username = principal.getName();
        final User currentUser = this.userService.getUserByEmail(username);
        final int id = currentUser.getId();
        final Pageable pageable = PageRequest.of(currentPage, 5);
        final Page<Contact> contacts = this.contactService.findAllContactsByUserId(id, pageable);
        model.addAttribute("totalPages",contacts.getTotalPages());
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("contacts",contacts);
        if (currentPage > contacts.getTotalPages())
            return "redirect:/users/dashboard";
        return "user-views/view-contacts";
    }

    @GetMapping("/viewContactDetails/{id}")
    public String viewContactDetails(@PathVariable("id") int id, Principal principal, HttpSession session, Model model) {
        final String username = principal.getName();
        final User currentUser = this.userService.getUserByEmail(username);
        final int currentUserId = currentUser.getId();
        final Contact contact = this.contactService.getContactById(id);
        final int contactUserId = contact.getUser().getId();
        if (currentUserId != contactUserId){
            System.out.println("You can not access someone else contact details...");
            return "redirect:/users/viewContacts/0";
        }
        model.addAttribute("title", "View Contact Details");
        model.addAttribute("contact", contact);
        return "user-views/view-contact-details";
    }

    @GetMapping("/editContact/{id}")
    public String editContact(@PathVariable("id") int id,Model model, Principal principal) {
        String currentUsername = principal.getName();
        final int currentUserId = this.userService.getUserByEmail(currentUsername).getId();
        final Contact contact = this.contactService.getContactById(id);
        final String username = contact.getUser().getEmail();
        final int contactUserId = contact.getUser().getId();
        System.out.println("User logged in: " + currentUsername);
        System.out.println("Trying to perform operation on contact with id: " + id + " belongs to: " + username);
        if (contactUserId != currentUserId)
            return "redirect:/users/viewContacts/0";
        model.addAttribute("title", "Edit Contact - Smart Contact Manager");
        model.addAttribute("contact", contact);
        return "user-views/edit-contact";
    }

    @PostMapping("/updateContact/{id}")
    public String updateContact(@PathVariable("id") int id, @Valid @ModelAttribute Contact contact,@RequestParam("contactImageFile") MultipartFile contactImageFile, Principal principal, HttpSession session, Model model) throws IOException {
        final Contact existingContact = this.contactService.getContactById(id);
        final int contactUserId = existingContact.getUser().getId();
        final User currentUser = this.userService.getUserByEmail(principal.getName());
        final int currentUserId = currentUser.getId();
        if (currentUserId != contactUserId)
            return "redirect:/users/viewContacts/0";
        contact.setId(id);

        if (contactImageFile.isEmpty()){
            System.out.println("No image selected for contact with id: " + id);
            contact.setImage("default.jpg");
        } else {
            final String contactImageName = contactImageFile.getOriginalFilename();
            contact.setImage(contactImageName);
            File contactImages = new ClassPathResource("/static/images").getFile();
            final Path path = Paths.get(contactImages.getAbsolutePath() + File.separator + contactImageName);
            Files.copy(contactImageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        contact.setUser(currentUser);
        final Contact updatedContact = this.contactService.updateContact(contact);
        System.out.println("Contact Updated Details: " + updatedContact);
        final List<Contact> contacts = this.contactService.getAllContactsByUserId(currentUserId);
        model.addAttribute("contacts", contacts);
        return "redirect:/users/viewContacts/0";
    }

    @GetMapping("/editProfile/{id}")
    public String editProfile(@PathVariable("id") int id, Model model, Principal principal) {
        final User requestedUser = this.userService.getUser(id);
        final String username = principal.getName();
        final User currentUser = this.userService.getUserByEmail(username);
        if (!requestedUser.getEmail().equals(username))
            return "redirect:/users/dashboard";
        model.addAttribute("user",currentUser);
        model.addAttribute("title", "Edit User Profile");
        return "user-views/edit-profile";
    }

    @PostMapping("/updateUserDetails/{id}")
    public String updateUserDetails(@Valid @ModelAttribute("user") User user,BindingResult result, @RequestParam("userImage") MultipartFile userImage, @PathVariable("id") int id, Principal principal, Model model, HttpSession session){
        try {
            final String username = principal.getName();
            final int currentUserId = this.userService.getUserByEmail(username).getId();
            session.removeAttribute("message");
            if (id != currentUserId)
                return "redirect:/users/dashboard";
            if (result.hasErrors()){
                model.addAttribute("user",user);
                return "user-views/edit-profile";
            }
            if (userImage.isEmpty()){
                user.setImageUrl("default-user.png");
            } else {
                final String userImageName = userImage.getOriginalFilename();
                user.setImageUrl(userImageName);
                final File usersDirectory = new ClassPathResource("/static/images/users").getFile();
                final Path path = Paths.get(usersDirectory.getAbsolutePath() + File.separator + userImageName);
                Files.copy(userImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            final User existingUser = this.userService.getUserByEmail(username);
            System.out.println("Previous User Details: " + existingUser);
            user.setUserRole(UserRole.USER);
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            final User updatedUser = this.userService.saveUser(user);
            System.out.println("Updated User Details: " + updatedUser);
            Message message = new Message("User details updated successfully.","alert-success");
            session.setAttribute("message",message);
            return "redirect:/users/dashboard";
        } catch (Exception e) {
            model.addAttribute("user",user);
            Message message = new Message("Failed to update user details " + e.getMessage(),"alert-danger");
            e.printStackTrace();
            session.setAttribute("message", message);
            return "user-views/edit-profile";
        }
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(Model model, @PathVariable("id") int id, Principal principal) {
        model.addAttribute("title", "Delete Contact - Smart Contact Manager");
        final String username = principal.getName();
        final User currentUser = this.userService.getUserByEmail(username);
        final int currentUserId = currentUser.getId();
        final Contact contact = this.contactService.getContactById(id);
        final int contactUserId = contact.getUser().getId();
        if (currentUserId != contactUserId)
            return "redirect:/users/viewContacts/0";
        this.contactService.deleteContact(id);
        final List<Contact> contacts = this.contactService.getAllContactsByUserId(currentUserId);
        model.addAttribute("contacts", contacts);
        return "redirect:/users/viewContacts/0";
    }
}
