package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getReferenceById(id);
    }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED) --- in real life, but not this demo
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable Long id){
        //Also need to check for child records before deleting
        speakerRepository.deleteById(id);
    }

    @PutMapping(value = "{id}")
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        // PUT = all attributes are passed in; PATCH = only needs attributes to be updated
        Speaker existingSpeaker = speakerRepository.getReferenceById(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}