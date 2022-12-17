package com.nure.ua.Volunteering_UA.controller.customer;


import com.nure.ua.Volunteering_UA.dto.customer.CustomerGetDto;
import com.nure.ua.Volunteering_UA.dto.organization.OrgDto;
import com.nure.ua.Volunteering_UA.dto.volunteer.VolunteerGetDto;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.cutomer.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/volunteering/customer")
@Api(value = "Operations with customer")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @ApiOperation(value = "Subscribe an organization")
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping("/{organizationName}")
  public ResponseEntity<String> register(
          @ApiParam(value = "Organization Name")
          @PathVariable String organizationName) {
    System.out.println(organizationName);
    customerService.subscribeOrganization(organizationName);
    return new ResponseEntity<>("Now you are subscriber of volunteering organization", HttpStatus.OK);
  }

  @ApiOperation(value = "Unsubscribe an organization")
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping()
  public ResponseEntity<String> unRegister(
          @ApiParam(name = "Organization Name")
          @RequestBody OrgDto organizationName) {
    customerService.unSubscribeOrganization(organizationName.getName());
    return new ResponseEntity<>("You're successfully unsubscribed", HttpStatus.OK);
  }

  @ApiOperation(value = "My account")
  @GetMapping("myAccount")
  public ResponseEntity<CustomerGetDto> myAccount() {
    Optional<CustomerGetDto> myAccount = customerService
            .myAccount();
    if (myAccount.isPresent()) {
      return new ResponseEntity<>(myAccount.get(), HttpStatus.OK);
    }
    throw new CustomException("It seems that you're not a customer", HttpStatus.BAD_REQUEST);
  }


}
