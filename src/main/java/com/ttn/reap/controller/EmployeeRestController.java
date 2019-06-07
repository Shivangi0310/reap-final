package com.ttn.reap.controller;

import com.ttn.reap.DTO.*;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.OrderHistory;
import com.ttn.reap.entity.Recognition;
import com.ttn.reap.entity.RevokeRecognitionDetails;
import com.ttn.reap.enums.ResponseCode;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.repository.OrderHistoryRepository;
import com.ttn.reap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    private RevokeRecognitionService revokeRecognitionService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @GetMapping("/users")
    public List<Employee> fetchAllUsers() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/findUserByName")
    public ResponseEntity<?> fetchRecognizee(@RequestParam String pattern) {
        System.out.println("hello");
        List<EmployeeSearchDTO> employees = null;
        try {
            employees = employeeService.findEmployeesWithNameMatchingPattern(pattern);
            System.out.print(employees.size());
        } catch (EmployeeException e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(e);
        }
        return ResponseEntity.ok().body(employees);
    }


    /*@PutMapping("buyItems")
    public ResponseEntity<?> buyItems(@RequestBody OrderDto dto, HttpSession session) {
        LoggedInUserDetails loggedInUser = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        OrderHistory orders = new OrderHistory();
        dto.setEmployeeId(loggedInUser.getId());
        GenericResponse response = new GenericResponse();
        Integer price=dto.getTotalPointsRedeemed();
        LoggedInUserDetails employee = new LoggedInUserDetails();

            if (itemService.updateUserItemPurchased(dto)) {
                response.setResponseCode(ResponseCode.API_SUCCESS);
              return  ResponseEntity.ok().body(response);
            }
            response.setResponseCode(ResponseCode.API_FAILURE);
        return ResponseEntity.ok().body(response);

    }*/

    @PutMapping("recogniseNewers")
    public ResponseEntity<?> giveRecognization(@RequestBody RecognizationDto dto, HttpSession session) {
        GenericResponse genericResponse = new GenericResponse();
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        dto.setFrom(loggedInUserDetails.getId().intValue());
        try {
            recognitionService.recognizeNewer(dto);
            genericResponse.setResponseCode(ResponseCode.API_SUCCESS);
        } catch (EmployeeException e) {
            return ResponseEntity.ok().body(e);

        }
        return ResponseEntity.ok().body(genericResponse);
    }


    @PutMapping("revokeRecog")
    public ResponseEntity<?> revokeRecognition(@RequestBody RevokeRecognitionDto dto, HttpServletRequest request) {

        GenericResponse genericResponse = new GenericResponse();
        RevokeRecognitionDetails revokeRecognitionDetails = revokeRecognitionService.revokeRecognition(dto);
        if (revokeRecognitionDetails != null) {
            //        Email work
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            String revokeReason = dto.getRevokeReason();
            //get information of sender and receiver of recognition to be revoked!
            Integer recogId = dto.getRecognitionId();

            Optional<Recognition> recognition = null;
            recognition = recognitionService.findByid(recogId);

            Employee sender = recognition.get().getRecognizerEmployeeId();
            Employee receiver = recognition.get().getRecognizeeId();

            // Email message
            SimpleMailMessage revokerecognitionEmail = new SimpleMailMessage();

            //revokerecognitionEmail.setTo(sender.getEmail());
            revokerecognitionEmail.setTo(receiver.getEmail(), sender.getEmail());
            revokerecognitionEmail.setSubject("Revokation of Recognition");
            revokerecognitionEmail.setText("The Recognition done by Recognizer: " + sender.getFirstName()
                    + " " + sender.getLastName() + " to Recognizee: " + receiver.getFirstName() + " " + receiver.getLastName() + " has been revoked!\n" + "Reason Of Revokation is: " + revokeReason);

            //Send mail
            emailService.sendEmail(revokerecognitionEmail);

            // Email done
            genericResponse.setResponseCode(ResponseCode.API_SUCCESS);
            return ResponseEntity.ok().body(genericResponse);
        }

        genericResponse.setStatus(400);
        genericResponse.setResponseCode(ResponseCode.API_FAILURE);
        return ResponseEntity.ok().body(genericResponse);
    }

    @GetMapping("recognitions")
    public ResponseEntity<?> findAll() {
        List<WallOfFameDto> dto = null;
        try {
            dto = recognitionService.findAllRecognitions();
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("fetchReceivedRecognitions/{id}")
    public ResponseEntity<?> fetchAllRecognitionReceivedByUser(@PathVariable("id") int id) {
        List<WallOfFameDto> dto = null;
        try {
            dto = recognitionService.listAllRecognitionsReceivedByUserId(id);
        } catch (EmployeeException e) {
            return  ResponseEntity.ok().body(e);
        }

        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("fetchSharedRecognitions")
    public ResponseEntity<?> fetchAllRecognitionSharedByLoggedInUser(HttpSession session) {
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        System.out.println(loggedInUserDetails.getId());

        List<RecognitionHistoryDto> recognitions = null;
        try {
            recognitions = recognitionService.listAllRecognitionsShared(loggedInUserDetails.getId());
        } catch (EmployeeException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(recognitions);
    }

    @GetMapping("fetchReceivedRecognitions")
    public ResponseEntity<?> fetchAllRecognitionReceivedByLoggedInUser(HttpSession session) {
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        System.out.println(loggedInUserDetails.getId());

        List<RecognitionHistoryDto> recognitions = null;
        try {
            recognitions = recognitionService.listAllRecognitionsReceived(loggedInUserDetails.getId());
        } catch (EmployeeException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(recognitions);
    }

    @GetMapping("fetchAllRecognitions")
    public ResponseEntity<?> fetchAllRecognitionOfLoggedInUser(HttpSession session) {
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        System.out.println(loggedInUserDetails.getId());
        List<RecognitionHistoryDto> recognitions = null;
        try {
            recognitions = recognitionService.listAllRecognitions(loggedInUserDetails.getId());
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(recognitions);
    }

    @GetMapping("fetchPoints")
    public ResponseEntity<?> fetchPoints(HttpSession session) {
        LoggedInUserDetails loggedInUser = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        LoggedInUserDetails user = new LoggedInUserDetails();
        user.setId(loggedInUser.getId());
        user.setPoints(loggedInUser.getPoints());
        return ResponseEntity.ok().body(user);
    }


    @GetMapping("admin/employees")
    public ResponseEntity<?> fetchEmployeeForAdmin() {
        List<Employee> users = employeeService.findAllEmployees();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("admin/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id,@RequestBody LoggedInUserDetails emp){

        GenericResponse response = new GenericResponse();
        return  ResponseEntity.ok().body(emp);

    }

}