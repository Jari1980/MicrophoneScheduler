package org.workshop.microphoneschedulerapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.microphoneschedulerapi.domain.entity.Microphone;
import org.workshop.microphoneschedulerapi.repository.MicrophoneRepository;

@Service
public class MicrophoneService {

    MicrophoneRepository microphoneRepository;

    @Autowired
    public MicrophoneService(MicrophoneRepository microphoneRepository) {
        this.microphoneRepository = microphoneRepository;
    }

    public void createMicrophone(String microphoneName){
        Microphone microphone = Microphone.builder()
                .microphoneName(microphoneName)
                .build();
        microphoneRepository.save(microphone);
    }

    public void deleteMicrophone(String microphoneName){
        microphoneRepository.delete(microphoneRepository.findByMicrophoneName(microphoneName).get(0));
    }

    public void updateMicrophone(int microphoneId, String newMicrophoneName){
        microphoneRepository.updateMicrophone(microphoneId, newMicrophoneName);
    }
}
