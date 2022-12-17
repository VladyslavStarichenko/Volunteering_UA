package com.nure.ua.Volunteering_UA.controller.volunteer;


import com.nure.ua.Volunteering_UA.dto.organization.OrgDto;
import com.nure.ua.Volunteering_UA.dto.volunteer.VolunteerGetDto;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import com.nure.ua.Volunteering_UA.service.volunteer.VolunteerService;
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
@RequestMapping("/volunteering/volunteer")
@Api(value = "Operations with volunteers")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class VolunteerController {

  private final VolunteerService volunteerService;
  private final UserServiceSCRT userServiceSCRT;
  private final OrganizationRepository organizationRepository;

  @Autowired
  public VolunteerController(VolunteerService volunteerService, UserServiceSCRT userServiceSCRT, OrganizationRepository organizationRepository) {
    this.volunteerService = volunteerService;
    this.userServiceSCRT = userServiceSCRT;
    this.organizationRepository = organizationRepository;
  }

  @ApiOperation(value = "Register in organization")
  @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
  @PutMapping("/organizationName/{organizationName}")
  public ResponseEntity<String> register(
          @ApiParam(value = "Organization Name")
          @PathVariable String organizationName) {
    Optional<Organization> organization = organizationRepository
            .getOrganizationByName(organizationName);
    organization
            .ifPresent(organization2Register ->
                    volunteerService
                            .registerInOrganization(
                                    organization2Register,
                                    userServiceSCRT.getCurrentLoggedInUser()
                            ));
    return new ResponseEntity<>("Now you are member of volunteering organization", HttpStatus.OK);
  }

  @ApiOperation(value = "UnRegister in organization")
  @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
  @PutMapping("unregister/")
  public ResponseEntity<String> unRegister(
          @ApiParam(name = "Organization Name")
          @RequestBody OrgDto organizationName) {
    Optional<Organization> organization = organizationRepository
            .getOrganizationByName(organizationName.getName());
    organization
            .ifPresent(organization2Register ->
                    volunteerService
                            .unRegisterInOrganization(
                                    organization2Register,
                                    userServiceSCRT.getCurrentLoggedInUser()
                            ));
    return new ResponseEntity<>("You're successfully unregistered", HttpStatus.OK);
  }

  @ApiOperation(value = "My account")
  @GetMapping("myAccount")
  public ResponseEntity<VolunteerGetDto> myAccount() {
    Optional<VolunteerGetDto> myAccount = volunteerService
            .getMyAccount(userServiceSCRT.getCurrentLoggedInUser());
    if(myAccount.isPresent()){
      return new ResponseEntity<>(myAccount.get(),HttpStatus.OK);
    }
    throw new CustomException("It seems that you're not a volunteer", HttpStatus.BAD_REQUEST);
  }


}
