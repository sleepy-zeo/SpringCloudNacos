package com.sleepy.oauth2.api;

import com.sleepy.oauth2.vo.Contact;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/contacts")
public class UserContactsController {

    @ResponseBody
    @RequestMapping("")
    @PreAuthorize("#oauth2.hasScope('write')")
    List<Contact> contacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact("fcq", "18913960590"));
        contactList.add(new Contact("fcq2", "18913960591"));
        contactList.add(new Contact("fcq2", "18913960592"));
        return contactList;
    }
}
