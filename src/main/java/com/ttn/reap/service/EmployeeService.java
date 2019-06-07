package com.ttn.reap.service;

import com.ttn.reap.DTO.EmployeeSearchDTO;
import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.enums.ExceptionStatus;
import com.ttn.reap.enums.Role;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    //    saving an employee with other internal details into the db while registration!
    public Employee saveEmployee(@Valid Employee employee, MultipartFile multipartFile)
            throws IOException, EmployeeException {

        if (employeeRepository.findByEmail(employee.getEmail()) != null) {
            throw new EmployeeException("Email Already Registered", ExceptionStatus.EMPLOYEE_ALREADY_EXIST);
        }
        employee.setActive(Boolean.TRUE);
        if (employee.getEmail().equals("reapttn@gmail.com")) {
            employee.getRoleSet().add(Role.ADMIN);
        } else
            employee.getRoleSet().add(Role.USER);
        assignBadges(employee);
        String photoPath = saveProfilePhotoPath(multipartFile);
        System.out.println(photoPath);
        employee.setProfilePhoto(photoPath);
        employee.setNoOfBronzeBadgeEarned(0);
        employee.setNoOfSilverBadgeEarned(0);
        employee.setNoOfGoldBadgeEarned(0);
        Integer points = calculatePoints(employee);
        employee.setPoints(points);
        return employeeRepository.save(employee);

    }

    //    setting badges employee are allowed to give based on their roles
    Employee assignBadges(Employee employee) {
        if (employee.getRoleSet().contains(Role.PRACTICE_HEAD)) {
            employee.setGoldBadgeCount(3);
            employee.setSilverBadgeCount(6);
            employee.setBronzeBadgeCount(9);
        } else if (employee.getRoleSet().contains(Role.SUPERVISOR)) {
            employee.setGoldBadgeCount(2);
            employee.setSilverBadgeCount(3);
            employee.setBronzeBadgeCount(6);
        } else {
            employee.setGoldBadgeCount(1);
            employee.setSilverBadgeCount(2);
            employee.setBronzeBadgeCount(3);
        }
        return employee;
    }


    //    for calcuting points of employees based on their no of badge earned.
    Integer calculatePoints(@Valid Employee employee) {
        Integer points;
        points = employee.getNoOfGoldBadgeEarned() * Badge.GOLD.getBadgeWeight() +
                employee.getNoOfSilverBadgeEarned() * Badge.SILVER.getBadgeWeight()
                + employee.getNoOfBronzeBadgeEarned() * Badge.BRONZE.getBadgeWeight();

        return points;
    }

    //    for finding all employee for search functionality
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    //    for checking whether the employee is already registered or not....
    //    for registration and login purpose
    //    also for the search functionality
    public Employee findEmployeeByEmailId(String email) {
        return employeeRepository.findByEmail(email);
    }

    //    for search functionality
    public Optional<List<Employee>> findEmployeeByFirstName(String firstname) {
        return employeeRepository.findByFirstName(firstname);
    }

    //    for search functionality
    public Optional<List<Employee>> findEmployeeByLastName(String lastname) {

        return employeeRepository.findByLastName(lastname);
    }

    //    for saving profile picture uploaded by the employee while registration
    public String saveProfilePhotoPath(MultipartFile profilePhoto) throws IOException {


        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        String photoPath ="/home/ttn/Desktop/Reap-all-projects/reap-project-edit/out/production/resources/static/emp-imgs/";

//        String photoPath = currentWorkingDir.toString() + "/src/main/resources/static/emp-imgs/";
        byte[] photoBytes = profilePhoto.getBytes();
        Path path = Paths.get(photoPath + profilePhoto.getOriginalFilename());
        System.out.println(path.toString());
        Files.write(path, photoBytes);
        return "/emp-imgs/" + profilePhoto.getOriginalFilename();

    }

    //    for verifying login credentials for successful login
    public Employee loginEmployee(String email, String password) throws EmployeeException {

        Optional<Employee> employee = employeeRepository.findByEmailAndPassword(email, password);
        if (!employee.isPresent()) {
            throw new EmployeeException("Invalid Credentials", ExceptionStatus.INVALID_DETAILS);
        }
        return employee.get();
    }

    public List<EmployeeSearchDTO> findEmployeesWithNameMatchingPattern(String pattern) throws EmployeeException {
        Optional<List<Employee>> employees = employeeRepository.getAllUsers(pattern + "%");

        if (!employees.isPresent()) {
            throw new EmployeeException("No Data found", ExceptionStatus.NO_DATA_FOUND);
        }
        List<EmployeeSearchDTO> empData = new ArrayList<>();
        employees.get().stream().forEach(employee -> {
            EmployeeSearchDTO emp = new EmployeeSearchDTO();
            emp.setId(employee.getId());
            emp.setFullName(employee.getFirstName() + " " + employee.getLastName());
            emp.setEmail(employee.getEmail());
            emp.setProfilePic(employee.getProfilePhoto());
            empData.add(emp);
        });
        return empData;
    }

    // save employee after reseting password
    public Employee updateEmployeePassword(Employee employee) {
        return employeeRepository.save(employee);
    }

    //    forget password reset token
    public Optional<Employee> findEmployeeByResetToken(String resetToken) {
        return employeeRepository.findByResetToken(resetToken);
    }


    public Employee updatEmployeeAdmin(LoggedInUserDetails emp) {

        Employee employee = employeeRepository.findById(emp.getId()).get();

        if (emp.getPoints() != null)
            employee.setPoints(emp.getPoints());

        if (emp.getGoldBadgeCount() != null)
            employee.setGoldBadgeCount(emp.getGoldBadgeCount());
        if (emp.getSilverBadgeCount() != null)
            employee.setSilverBadgeCount(emp.getSilverBadgeCount());
        if (emp.getBronzeBadgeCount() != null)
            employee.setBronzeBadgeCount(emp.getBronzeBadgeCount());

        if (emp.getActive() != null) {
            if (emp.getActive() == Boolean.TRUE) {
                employee.setActive(Boolean.TRUE);
            } else
                employee.setActive(Boolean.FALSE);
        }

        if (emp.getRoles() != null) {
            emp.getRoles().forEach(e -> {
                switch (e) {
                    case 0:
                        employee.getRoleSet().add(Role.USER);
                    case 1:
                        employee.getRoleSet().add(Role.ADMIN);
                    case 2:
                        employee.getRoleSet().add(Role.SUPERVISOR);
                    case 3:
                        employee.getRoleSet().add(Role.PRACTICE_HEAD);
                }
            });
            assignBadges(employee);
        }

        return employeeRepository.save(employee);
    }

    // Deduct user's points when cart is checked out
    public Employee deductPointsOnCheckout(LoggedInUserDetails user, Integer deductiblePoints) {
        Employee employee = employeeRepository.findById(user.getId()).get();

        Integer currentPoints = employee.getPoints();
        employee.setPoints(currentPoints - deductiblePoints);
       return employeeRepository.save(employee);
    }

}
