package com.ttn.reap.controller;

import com.ttn.reap.entity.Employee;
import com.ttn.reap.service.EmailService;
import com.ttn.reap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/reap")
public class PasswordResetController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/forgotpwd")
    public String processForgotPasswordForm(RedirectAttributes redirectAttributes, HttpServletRequest request) {
// Look up for employee in database by email
        System.out.println("hello");
        String email = request.getParameter("email-pwd");
        System.out.println(email);
        Employee employee1 = employeeService.findEmployeeByEmailId(email);
        System.out.println(employee1);
        if (employee1==null) {
            redirectAttributes.addFlashAttribute("errorMessage", "We didn't find an account for that e-mail address.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/reap/login";
        } else {
// Generate random 36-character string token for reset password
            Employee resetUser = employee1;
            resetUser.setResetToken(UUID.randomUUID().toString());

// Save token to database
            employeeService.updateEmployeePassword(resetUser);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

// Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(resetUser.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reap/reset?token=" + resetUser.getResetToken());

//Send mail
            emailService.sendEmail(passwordResetEmail);

// Add success message to view
            redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to " + resetUser.getEmail());
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:login";
        }
    }
    
    String resetToken;
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        System.out.println("hello");
        Optional<Employee> employee = employeeService.findEmployeeByResetToken(token);
        System.out.println(token);
        if (employee.isPresent()) {
            resetToken=token;
            modelAndView.addObject("resetToken", token);
        } else {
            modelAndView.addObject("errorMessage", "Oops! This is an invalid password link.");

        }
        modelAndView.setViewName("passwordReset");
        return modelAndView;

    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView detNewPassword(ModelAndView modelAndView, HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {
        System.out.println("hello");
        Optional<Employee> employee = employeeService.findEmployeeByResetToken(resetToken);

        if (employee.isPresent()) {
            Employee resetEmployee = employee.get();
            String password = request.getParameter("password");
            System.out.println(password);
            resetEmployee.setPassword(password);

            resetEmployee.setResetToken(null);

            employeeService.updateEmployeePassword(resetEmployee);

            redirectAttributes.addFlashAttribute("successMessage",
                    "You have successfully reset your password. You may login name.");

            modelAndView.setViewName("redirect:login");
            return modelAndView;
        } else {
            modelAndView.addObject("errorMessage", "This is an invalid password reset link!!!");
            modelAndView.setViewName("passwordReset");
        }
        return modelAndView;
    }
    /*@ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView hadleMissingParameters(MissingServletRequestParameterException exception){
        return new ModelAndView("redirect:login");
    }*/
}


