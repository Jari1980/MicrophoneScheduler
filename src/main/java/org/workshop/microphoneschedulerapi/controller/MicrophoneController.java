package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import jdk.jfr.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.microphoneschedulerapi.domain.dto.MicrophoneUpdateDTOForm;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.service.MicrophoneService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "", allowPrivateNetwork = "")
@RequestMapping("/api/v1/microphone")
@RestController
public class MicrophoneController {

    MicrophoneService microphoneService;

    @Autowired
    public MicrophoneController(MicrophoneService microphoneService) {
        this.microphoneService = microphoneService;
    }

    /**
     * Method for creating microphone
     * Used in Microphone.jsx
     */
    @PostMapping("/createMicrophone")
    public ResponseEntity<Void> createMicrophone(String microphoneName) {
        if(microphoneName.isEmpty()|| microphoneName.isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            microphoneService.createMicrophone(microphoneName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for deleting microphone
     * Used in Microphone.jsx
     */
    @DeleteMapping("/deleteMicrophone{microphoneName}")
    public ResponseEntity<Void> deleteMicrophone(@PathParam("microphoneName") String microphoneName) {
        try {
            microphoneService.deleteMicrophone(microphoneName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for updating microphone
     * Used in Microphone.jsx
     */
    @PutMapping("/updateMicrophone")
    public ResponseEntity<Void> updateMicrophone(@RequestBody MicrophoneUpdateDTOForm form) {
        try{
            microphoneService.updateMicrophone(form.microphoneId(), form.microphoneName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for getting all microphones
     * Used in Microphone.jsx
     */
    @GetMapping("/getMicrophones")
    public ResponseEntity<List<Microphone>> getMicrophones() {
        try{
            List<Microphone> list = microphoneService.getAllMicrophones();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
