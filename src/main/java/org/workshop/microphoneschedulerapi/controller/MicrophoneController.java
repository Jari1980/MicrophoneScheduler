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

    @PostMapping("/createMicrophone{microphoneName}")
    public ResponseEntity<Void> createMicrophone(@PathParam("microphoneName") String microphoneName) {
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

    @DeleteMapping("/deleteMicrophone{microphoneName}")
    public ResponseEntity<Void> deleteMicrophone(@PathParam("microphoneName") String microphoneName) {
        try {
            microphoneService.deleteMicrophone(microphoneName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateMicrophone")
    public ResponseEntity<Void> updateMicrophone(@RequestBody MicrophoneUpdateDTOForm form) {
        try{
            microphoneService.updateMicrophone(form.microphoneId(), form.microphoneName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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
