package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getReferenceById(id);
    }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED) --- in real life, but not this demo
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable Long id){
        //Also need to check for child records before deleting
        sessionRepository.deleteById(id);
    }

    @PutMapping(value = "{id}")
    public Session update(@PathVariable Long id, @RequestBody Session session){
        // PUT = all attributes are passed in; PATCH = only needs attributes to be updated
        Session existingSession = sessionRepository.getReferenceById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
