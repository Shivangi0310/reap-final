package com.ttn.reap.service;

import com.ttn.reap.DTO.RecognitionHistoryDto;
import com.ttn.reap.DTO.RecognizationDto;
import com.ttn.reap.DTO.WallOfFameDto;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Recognition;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.ExceptionStatus;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.repository.EmployeeRepository;
import com.ttn.reap.repository.RecognitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecognitionService {

    @Autowired
    private RecognitionRepository recognitionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public Optional<Recognition> findByid(Integer id) {
        return recognitionRepository.findById(id);
    }

    public List<WallOfFameDto> findAllRecognitions() throws EmployeeException {

        List<WallOfFameDto> dto = new ArrayList<>();

        List<Recognition> all = recognitionRepository.findByActiveIsTrue();
        if (all.isEmpty())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);

        all.stream().forEach(e -> {
            WallOfFameDto wall = new WallOfFameDto();
            wall.setRecognizeeName(e.getRecognizeeId().getFirstName() + " " + e.getRecognizeeId().getLastName());
            wall.setBadge(e.getBadge().toString());
            wall.setMessage(e.getMessage());
            wall.setRecognizeePic(e.getRecognizeeId().getProfilePhoto());
            wall.setDate(e.getDateOfRecognition());
            wall.setRevokeId(e.getId().intValue());
            wall.setRecognizorName(e.getRecognizerEmployeeId().getFirstName() + " " + e.getRecognizerEmployeeId().getLastName());
            dto.add(wall);
        });

        return dto;
    }


    //    fetch all user specific shared recognitions
    public List<RecognitionHistoryDto> listAllRecognitionsShared(Integer id) throws EmployeeException {
        List<RecognitionHistoryDto> dto = new ArrayList<>();
        List<Recognition> shared = recognitionRepository.fetchShared(id.intValue()).get();
        if (shared.isEmpty())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);
        shared.stream().forEach(e -> {
            RecognitionHistoryDto historyDto = new RecognitionHistoryDto();
            historyDto.setRecognizeeName(e.getRecognizeeId().getFirstName() + " " + e.getRecognizeeId().getLastName());
            historyDto.setRecognizorName(e.getRecognizerEmployeeId().getFirstName() + " " + e.getRecognizerEmployeeId().getLastName());
            historyDto.setBadge(e.getBadge().toString());
            historyDto.setMessage(e.getMessage());
            historyDto.setRecognizeePic(e.getRecognizeeId().getProfilePhoto());
            historyDto.setDate(e.getDateOfRecognition());
            dto.add(historyDto);
        });

        return dto;
    }


//    public List<Recognition> listAllRecognitionsShared(Integer id){
//       return recognitionRepository.fetchShared(id.intValue()).get() ;
//    }

    //    fetch all user specific received recognitions
    public List<RecognitionHistoryDto> listAllRecognitionsReceived(Integer id) throws EmployeeException {
        List<RecognitionHistoryDto> dto = new ArrayList<>();
        List<Recognition> shared = recognitionRepository.fetchReceived(id.intValue()).get();
        if (shared.isEmpty())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);
        shared.stream().forEach(e -> {
            RecognitionHistoryDto historyDto = new RecognitionHistoryDto();
            historyDto.setRecognizeeName(e.getRecognizeeId().getFirstName() + " " + e.getRecognizeeId().getLastName());
            historyDto.setRecognizorName(e.getRecognizerEmployeeId().getFirstName() + " " + e.getRecognizerEmployeeId().getLastName());
            historyDto.setBadge(e.getBadge().toString());
            historyDto.setMessage(e.getMessage());
            historyDto.setRecognizeePic(e.getRecognizeeId().getProfilePhoto());
            historyDto.setDate(e.getDateOfRecognition());
            dto.add(historyDto);
        });

        return dto;
    }

    public List<WallOfFameDto> listAllRecognitionsReceivedByUserId(Integer id) throws EmployeeException {
        List<WallOfFameDto> dto = new ArrayList<>();

        Optional<List<Recognition>> recognitionList = recognitionRepository.fetchReceived(id.intValue());

        if (!recognitionList.isPresent())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);

        List<Recognition> all = recognitionList.get();
        all.stream().forEach(e -> {
            if (e.getActive() == Boolean.TRUE) {
                WallOfFameDto wall = new WallOfFameDto();
                wall.setRecognizeeName(e.getRecognizeeId().getFirstName() + " " + e.getRecognizeeId().getLastName());
                wall.setBadge(e.getBadge().toString());
                wall.setMessage(e.getMessage());
                wall.setRecognizeePic(e.getRecognizeeId().getProfilePhoto());
                wall.setDate(e.getDateOfRecognition());
                wall.setRevokeId(e.getId().intValue());
                wall.setRecognizorName(e.getRecognizerEmployeeId().getFirstName() + " " + e.getRecognizerEmployeeId().getLastName());
                dto.add(wall);
            }
        });

        if (dto.isEmpty())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);

        return dto;
    }

//    public List<Recognition> listAllRecognitionsReceived(Integer id){
//        return recognitionRepository.fetchReceived(id.intValue()).get() ;
//    }


    public List<RecognitionHistoryDto> listAllRecognitions(Integer id) throws EmployeeException {
        List<RecognitionHistoryDto> dto = new ArrayList<>();
        List<Recognition> shared = recognitionRepository.getAllRecognitions(id.intValue()).get();
        if (shared.isEmpty())
            throw new EmployeeException("No data Found", ExceptionStatus.NO_DATA_FOUND);
        shared.stream().forEach(e -> {
            RecognitionHistoryDto historyDto = new RecognitionHistoryDto();
            historyDto.setRecognizeeName(e.getRecognizeeId().getFirstName() + " " + e.getRecognizeeId().getLastName());
            historyDto.setRecognizorName(e.getRecognizerEmployeeId().getFirstName() + " " + e.getRecognizerEmployeeId().getLastName());
            historyDto.setBadge(e.getBadge().toString());
            historyDto.setMessage(e.getMessage());
            historyDto.setRecognizeePic(e.getRecognizeeId().getProfilePhoto());
            historyDto.setDate(e.getDateOfRecognition());
            dto.add(historyDto);
        });

        return dto;
    }

//    public List<Recognition> listAllRecognitions(Integer id){
//        return recognitionRepository.getAllRecognitions(id.intValue()).get() ;
//    }


    public List<Recognition> showRecognitionByDate(Date startDate, Date endDate)
    {
        return recognitionRepository.findByDateOfRecognitionBetween(startDate,endDate);
    }

    public void recognizeNewer(RecognizationDto dto) throws EmployeeException {

        if (dto.getFrom() == dto.getTo())
            throw new EmployeeException("Can not give recognization to self", ExceptionStatus.CAN_NOT_BE_SAME);

        Optional<Employee> byIdTo = employeeRepository.findById(Integer.valueOf(dto.getTo()));
        Optional<Employee> byIdFrom = employeeRepository.findById(Integer.valueOf(dto.getFrom()));

        Badge badge1 = Badge.valueOf(dto.getBadge());
        System.out.println(badge1);
        Employee recognizer = byIdFrom.get();
        Employee recognizee = byIdTo.get();
        Recognition recognition = new Recognition();
        recognition.setBadge(badge1);
        recognition.setMessage(dto.getMessage());
        recognition.setRecognizeeId(recognizee);
        recognition.setRecognizerEmployeeId(recognizer);
        switch (badge1) {
            case GOLD: {
                if (recognizer.getGoldBadgeCount() == 0)
                    throw new EmployeeException("Insufficient Badge to give", ExceptionStatus.BADGE_INSUFFICIENT);
                recognizer.setGoldBadgeCount(recognizer.getGoldBadgeCount() - 1);
                recognizee.setNoOfGoldBadgeEarned(recognizee.getNoOfGoldBadgeEarned() + 1);
                recognizee.setPoints(recognizee.getPoints() + badge1.getBadgeWeight());
                System.out.println("i am here");
                break;
            }
            case BRONZE: {
                if (recognizer.getBronzeBadgeCount() == 0)
                    throw new EmployeeException("Insufficient Badge to give", ExceptionStatus.BADGE_INSUFFICIENT);
                recognizer.setBronzeBadgeCount(recognizer.getBronzeBadgeCount() - 1);
                recognizee.setNoOfBronzeBadgeEarned(recognizee.getNoOfBronzeBadgeEarned() + 1);
                recognizee.setPoints(recognizee.getPoints() + badge1.getBadgeWeight());
                break;
            }
            case SILVER: {
                if (recognizer.getSilverBadgeCount() == 0)
                    throw new EmployeeException("Insufficient Badge to give", ExceptionStatus.BADGE_INSUFFICIENT);
                recognizer.setSilverBadgeCount(recognizer.getSilverBadgeCount() - 1);
                recognizee.setNoOfSilverBadgeEarned(recognizee.getNoOfSilverBadgeEarned() + 1);
                recognizee.setPoints(recognizee.getPoints() + badge1.getBadgeWeight());
                break;
            }
        }
        employeeRepository.save(recognizer);
        employeeRepository.save(recognizee);
        recognitionRepository.save(recognition);
    }


}