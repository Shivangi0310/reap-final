package com.ttn.reap.service;

import com.ttn.reap.DTO.RevokeRecognitionDto;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Recognition;
import com.ttn.reap.entity.RevokeRecognitionDetails;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.EmployeeRepository;
import com.ttn.reap.repository.RecognitionRepository;
import com.ttn.reap.repository.RevokeRecognitionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevokeRecognitionService {

    @Autowired
    private RevokeRecognitionDetailsRepository revokeRecognitionDetailsRepository;

    @Autowired
    private RecognitionRepository recognitionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public RevokeRecognitionDetails revokeRecognition(RevokeRecognitionDto Dto) {
        Optional<Recognition> recognition = recognitionRepository.findById(Integer.valueOf(Dto.getRecognitionId()));
        Employee recognizee = recognition.get().getRecognizeeId();
        Employee recognizer = recognition.get().getRecognizerEmployeeId();
        Badge badge = recognition.get().getBadge();
        recognition.get().setActive(Boolean.FALSE);
        int pointsGiven = badge.getBadgeWeight();
        switch (badge) {
            case GOLD: {
                recognizer.setGoldBadgeCount(recognizer.getGoldBadgeCount() + 1);
                recognizee.setNoOfGoldBadgeEarned(recognizee.getNoOfGoldBadgeEarned() - 1);
                recognizee.setPoints(recognizee.getPoints() - pointsGiven);
                System.out.println("i am here");
                break;
            }
            case BRONZE: {
                recognizer.setBronzeBadgeCount(recognizer.getBronzeBadgeCount() + 1);
                recognizee.setNoOfBronzeBadgeEarned(recognizee.getNoOfBronzeBadgeEarned() - 1);
                recognizee.setPoints(recognizee.getPoints() - pointsGiven);
                break;
            }
            case SILVER: {
                recognizer.setSilverBadgeCount(recognizer.getSilverBadgeCount() + 1);
                recognizee.setNoOfSilverBadgeEarned(recognizee.getNoOfSilverBadgeEarned() - 1);
                recognizee.setPoints(recognizee.getPoints() - pointsGiven);
                break;
            }
        }

        String message = Dto.getRevokeReason();
        RevokeRecognitionDetails revoke = new RevokeRecognitionDetails();
        revoke.setRevokeReason(message);
        revoke.setRecognition(recognition.get());
        recognitionRepository.save(recognition.get());
        employeeRepository.save(recognizee);
        employeeRepository.save(recognizer);

        return revokeRecognitionDetailsRepository.save(revoke);
    }

}
