package com.nure.ua.Volunteering_UA.controller.request;


import com.nure.ua.Volunteering_UA.dto.request.AidReqConf;
import com.nure.ua.Volunteering_UA.dto.request.AidRequestDto;
import com.nure.ua.Volunteering_UA.dto.request.AidRequestPageResponse;
import com.nure.ua.Volunteering_UA.dto.request.RequestCreateDto;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Request_Status;
import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.repository.customer.CustomerRepository;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.request.Aid_Request_Service;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteering/request")
@Api(value = "Operations with requests")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class AidRequestController {

  private final Aid_Request_Service aid_request_service;
  private final UserServiceSCRT userServiceSCRT;
  private final CustomerRepository customerRepository;
  private final OrganizationRepository organizationRepository;

  @Autowired
  public AidRequestController(Aid_Request_Service aid_request_service, UserServiceSCRT userServiceSCRT, CustomerRepository customerRepository, OrganizationRepository organizationRepository) {
    this.aid_request_service = aid_request_service;
    this.userServiceSCRT = userServiceSCRT;
    this.customerRepository = customerRepository;
    this.organizationRepository = organizationRepository;
  }

  @ApiOperation(value = "Create request")
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PostMapping
  public ResponseEntity<AidRequestDto> createRequest(@ApiParam(value = "Request object to create")
                                                     @RequestBody RequestCreateDto requestCreateDto) {
    User currentLoggedInUser = userServiceSCRT.getCurrentLoggedInUser();
    Optional<Customer> customerDb = customerRepository.findByUser(currentLoggedInUser);
    Optional<Organization> organizationByName = organizationRepository
            .getOrganizationByName(requestCreateDto.getOrganizationName());
    if (customerDb.isPresent() && organizationByName.isPresent()) {
      Aid_Request aid_request = new Aid_Request();
      aid_request.setRequestStatus(Request_Status.VERIFICATION);
      aid_request.setAmount(requestCreateDto.getAmount());
      aid_request.setCustomer(customerDb.get());
      aid_request.setTitle(requestCreateDto.getTitle());
      aid_request.setDescription(requestCreateDto.getDescription());
      aid_request.setOrganization(organizationByName.get());
      aid_request.setVolunteering_type(requestCreateDto.getVolunteering_type());
      return new ResponseEntity<>(aid_request_service.createRequest(aid_request), HttpStatus.OK);
    }
    throw new CustomException("Error while creation of request", HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Approve request")
  @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
  @PutMapping("{requestId}")
  public ResponseEntity<AidRequestDto> approve(@PathVariable String requestId) {
    System.out.println();
    System.out.println(requestId);

    Optional<Aid_Request> requestDb = aid_request_service.getRequestById(Long.parseLong(requestId));
    if(requestDb.isPresent()){
      return new ResponseEntity<>(aid_request_service.approve(requestDb.get()), HttpStatus.OK);
    }
    throw new CustomException("Error while creation of request", HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Deliver request")
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping("/delivered")
  public ResponseEntity<AidRequestDto> finish(@ApiParam(name = "Object to confirm") @RequestBody AidReqConf aidReqConf) {
    Optional<Aid_Request> requestDb = aid_request_service.getRequestById(aidReqConf.getId());
    if(requestDb.isPresent()){
      return new ResponseEntity<>(aid_request_service.finish(requestDb.get(), aidReqConf.getCode()), HttpStatus.OK);
    }
    throw new CustomException("Error while creation of request", HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get all all requests")
  @GetMapping("/allRequests/{pageNumber}/{pageSize}")
  public ResponseEntity<AidRequestPageResponse> getAllRequests(
          @ApiParam(value = "Page number to show") @PathVariable int pageNumber,
          @ApiParam(value = "Page size") @PathVariable int pageSize
  ) {
    return new ResponseEntity<>(aid_request_service.getAllRequests(pageNumber,pageSize), HttpStatus.OK);
  }

  @ApiOperation(value = "Get all all requests by customer")
  @GetMapping("/allRequests/{customerId}")
  public ResponseEntity<List<AidRequestDto>> getAllRequestsByCustomerId(
          @ApiParam(value = "Customer id") @PathVariable Long customerId
  ) {
    return new ResponseEntity<>(aid_request_service.getAllRequestsByCustomerId(customerId), HttpStatus.OK);
  }

  @ApiOperation(value = "Get all all requests")
  @GetMapping("/allRequests/{pageNumber}/{pageSize}/{name}")
  public ResponseEntity<AidRequestPageResponse> getAllRequestsByOrganization(
          @ApiParam(value = "Page number to show") @PathVariable int pageNumber,
          @ApiParam(value = "Page size") @PathVariable int pageSize,
          @ApiParam(value = "Organization name") @PathVariable String name
  ) {
    return new ResponseEntity<>(aid_request_service.getAllRequestsByOrganization(pageNumber,pageSize,name), HttpStatus.OK);
  }


}
