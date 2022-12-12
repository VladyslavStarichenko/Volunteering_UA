package com.nure.ua.Volunteering_UA.controller.admin;


import com.nure.ua.Volunteering_UA.dto.AuthenticationDto;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.exeption.EmptyDataException;
import com.nure.ua.Volunteering_UA.model.user.Status;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.repository.user.UserRepository;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@Api(value = "Admin operations")
@RequestMapping(value = "/admin/")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class AdminController {

  private final UserServiceSCRT userServiceSCRT;
  private final UserRepository userRepository;

  @Autowired
  public AdminController(UserServiceSCRT userServiceSCRT, UserRepository userRepository) {
    this.userServiceSCRT = userServiceSCRT;
    this.userRepository = userRepository;
  }

  @PostMapping("registerComplexAdmin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "Register Admin")
  public ResponseEntity<Map<Object, Object>> signUp(@ApiParam(value = "User object to sign up to the system") @RequestBody AuthenticationDto user) {
    if (user == null) {
      throw new EmptyDataException("Invalid or empty input");
    }
    Map<Object, Object> response = userServiceSCRT.signUpOrganizationAdmin(user.toUser());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }


  @PutMapping("blockComplexAdmin/{adminName}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "Register Admin")
  public ResponseEntity<String> block(@ApiParam(value = "User object to sign up to the system") @PathVariable String adminName) {
    Optional<User> userDb = userRepository.findUserByUserName(adminName);
    if (userDb.isPresent()) {
      User user = userDb.get();
      user.setStatus(Status.NONACTIVE);
      userRepository.save(user);
    } else {
      throw new CustomException("There is no admin with specified name", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>("User with name: " + userDb.get().getUserName() + " was successfully blocked",
            HttpStatus.CREATED);
  }


}
