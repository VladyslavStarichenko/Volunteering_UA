package com.nure.ua.Volunteering_UA.controller.organization;

import com.nure.ua.Volunteering_UA.dto.organization.OrganizationCreateDto;
import com.nure.ua.Volunteering_UA.dto.organization.OrganizationGetDto;
import com.nure.ua.Volunteering_UA.dto.organization.OrganizationPageResponse;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.model.Statistic;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.organization.OrganizationService;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteering/organization")
@Api(value = "Operations with exercises")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
@Slf4j
public class OrganizationController {

  private final OrganizationService organizationService;
  private final OrganizationRepository organizationRepository;

  private final UserServiceSCRT userServiceSCRT;

  @Autowired
  public OrganizationController(OrganizationService organizationService, OrganizationRepository organizationRepository, UserServiceSCRT userServiceSCRT) {
    this.organizationService = organizationService;
    this.organizationRepository = organizationRepository;
    this.userServiceSCRT = userServiceSCRT;
  }

  @ApiOperation(value = "Get my volunteering organization")
  @PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
  @GetMapping
  public ResponseEntity<OrganizationGetDto> getMyOrganization() {
    Optional<OrganizationGetDto> myOrganization = organizationService.getMyOrganization();
    if (myOrganization.isPresent()) {
      OrganizationGetDto organizationGetDto = myOrganization.get();
      Statistic statistic = organizationGetDto.getStatistic();
      organizationGetDto.setStatistic(statistic);
      return new ResponseEntity<>(organizationGetDto, HttpStatus.OK);
    }
    throw new CustomException("You're not an owner of organization", HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get volunteering organization by name")
  @GetMapping("name/{organizationName}")
  public ResponseEntity<OrganizationGetDto> getOrganizationByName(@PathVariable String organizationName) {
    System.out.println(organizationName);
    Optional<OrganizationGetDto> myOrganization = organizationService.getOrganizationByName(organizationName);
    if (myOrganization.isPresent()) {
      OrganizationGetDto organizationGetDto = myOrganization.get();
      Statistic statistic = organizationGetDto.getStatistic();
      organizationGetDto.setStatistic(statistic);
      return new ResponseEntity<>(organizationGetDto, HttpStatus.OK);
    }
    throw new CustomException("There is no organization with name provided", HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get volunteering organizations by volunteer")
  @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
  @GetMapping("/workingOrganizations")
  public ResponseEntity<List<OrganizationGetDto>> getOrganizationsByVolunteer() {
    List<OrganizationGetDto> organizationByVolunteer = organizationService
            .getOrganizationByVolunteer(userServiceSCRT.getCurrentLoggedInUser().getId());
    if (organizationByVolunteer.isEmpty()) {
      throw new CustomException("There is no organizations in your work account", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(organizationByVolunteer, HttpStatus.OK);
  }

  @ApiOperation(value = "Get volunteering organizations by volunteering type")
  @GetMapping("/volunteeringType/{volunteeringType}")
  public ResponseEntity<List<OrganizationGetDto>> getOrganizationsByVolunteeringType( @PathVariable Volunteering_Type volunteeringType) {
    List<OrganizationGetDto> organizationByType = organizationService.getOrganizationByType(volunteeringType);
    if (organizationByType.isEmpty()) {
      throw new CustomException("There is no organizations with type provided", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(organizationByType, HttpStatus.OK);
  }

  @ApiOperation(value = "Get all volunteering organizations")
  @GetMapping("/allOrganizations/{pageNumber}/{pageSize}")
  public ResponseEntity<OrganizationPageResponse> getAllOrganizations(
          @ApiParam(value = "Page number to show") @PathVariable int pageNumber,
          @ApiParam(value = "Page size") @PathVariable int pageSize
  ) {
    return new ResponseEntity<>(organizationService
            .getAllOrganizations(pageNumber, pageSize), HttpStatus.OK);
  }

  @ApiOperation(value = "Delete my Organizations")
  @PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
  @DeleteMapping("/deleteOrganization")
  public ResponseEntity<String> deleteOrganization() {
    if (organizationService.delete()) {
      return new ResponseEntity<>("Organization Successfully deleted", HttpStatus.OK);
    }
    return new ResponseEntity<>("You don't own any organizations", HttpStatus.OK);
  }

  @ApiOperation(value = "Create organization")
  @PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
  @PostMapping
  public ResponseEntity<OrganizationGetDto> createOrganization(@ApiParam(value = "Organization object to create")
                                                          @RequestBody OrganizationCreateDto organizationCreateDto) {
    User currentLoggedInUser = userServiceSCRT.getCurrentLoggedInUser();
    if (organizationService.getMyOrganization().isPresent()) {
      throw new CustomException("You're already an organization", HttpStatus.BAD_REQUEST);
    }
    if (organizationRepository.existsByName(organizationCreateDto.getName())) {
      throw new CustomException("House Complex with same name is already exists", HttpStatus.IM_USED);
    }
    OrganizationGetDto organization = organizationService.createOrganization(
            currentLoggedInUser,
            organizationCreateDto.getName(),
            organizationCreateDto.getVolunteering_type()
    );
    organization.setStatistic(new Statistic());
    return new ResponseEntity<>(organization, HttpStatus.CREATED);
  }
}
