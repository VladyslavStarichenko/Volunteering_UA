package com.nure.ua.Volunteering_UA.controller.authentication;

import com.nure.ua.Volunteering_UA.dto.AuthenticationDto;
import com.nure.ua.Volunteering_UA.dto.AuthorizationDto;
import com.nure.ua.Volunteering_UA.dto.customer.CustomerGetDto;
import com.nure.ua.Volunteering_UA.dto.volunteer.VolunteerGetDto;
import com.nure.ua.Volunteering_UA.exeption.EmptyDataException;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Api(value = "Authentication operations (login, sign up)")
@RequestMapping(value = "/auth/")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class AuthenticationController {


    private final UserServiceSCRT userService;

    @Autowired
    public AuthenticationController(UserServiceSCRT userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    @ApiOperation(value = "Login to the system")
    public ResponseEntity<Map<Object, Object>> login(@ApiParam(value = "Registered User object") @RequestBody AuthorizationDto requestDto) {
        if (requestDto == null) {
            throw new UsernameNotFoundException("Invalid or empty input");
        }
        Map<Object, Object> response = userService.signIn(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("registerVolunteer")
    @ApiOperation(value = "Register Volunteer")
    public ResponseEntity<VolunteerGetDto> registerVolunteer(@ApiParam(value = "User object to sign up to the system") @RequestBody AuthenticationDto user) {
        if (user == null) {
            throw new EmptyDataException("Invalid or empty input");
        }
        VolunteerGetDto volunteerGetDto = userService.signUpVolunteer(user.toUser());
        return new ResponseEntity<>(volunteerGetDto, HttpStatus.CREATED);
    }

    @PostMapping("registerCustomer")
    @ApiOperation(value = "Register Customer")
    public ResponseEntity<CustomerGetDto> registerCustomer(@ApiParam(value = "User object to sign up to the system") @RequestBody AuthenticationDto user) {
        if (user == null) {
            throw new EmptyDataException("Invalid or empty input");
        }
        CustomerGetDto customerGetDto = userService.signUpCustomer(user.toUser());
        return new ResponseEntity<>(customerGetDto, HttpStatus.CREATED);
    }
}