package org.workshop.microphoneschedulerapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.microphoneschedulerapi.domain.dto.DirectorOverviewDTO;
import org.workshop.microphoneschedulerapi.service.DirectorService;

@RestController
@RequestMapping("/api/v1/director")
public class DirectorController {

    private DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }


    @GetMapping("/getoverview")
    public ResponseEntity<DirectorOverviewDTO> getOverview(@PathParam("playName") String playName) {
        try{
            DirectorOverviewDTO directorOverviewList = directorService.getOverview(playName);
            return new ResponseEntity<>(directorOverviewList, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
