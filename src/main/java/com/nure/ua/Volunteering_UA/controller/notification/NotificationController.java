package com.nure.ua.Volunteering_UA.controller.notification;

import com.nure.ua.Volunteering_UA.dto.notification.NotificationGetDto;
import com.nure.ua.Volunteering_UA.model.Notification;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.notifications.NotificationService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/volunteering/notification")
@Api(value = "Operations with notifications")
@CrossOrigin(origins = {"http://localhost:3000", "http://someserver:8000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class NotificationController {

  private final NotificationService notificationService;
  private final OrganizationRepository organizationRepository;

  @Autowired
  public NotificationController(NotificationService notificationService, OrganizationRepository organizationRepository) {
    this.notificationService = notificationService;
    this.organizationRepository = organizationRepository;
  }


  @ApiOperation(value = "Create Notifications for customers")
  @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
  @PostMapping("/{organizationName}/{text}")
  public ResponseEntity<String> notifyCustomers(
          @ApiParam(value = "Organization name") @PathVariable String organizationName,
          @ApiParam(value = "Message") @PathVariable String text
  ) {
    Optional<Organization> organizationDb = organizationRepository.getOrganizationByName(organizationName);
    if (organizationDb.isPresent()) {
      Organization organization = organizationDb.get();
      organization.getSubscribers()
              .forEach(customer -> notificationService.createNotification(new Notification(customer, text)));
    }
    return new ResponseEntity<>("Notifications was successfully sent ", HttpStatus.CREATED);
  }


  @ApiOperation(value = "Get My Notifications")
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping("myNotifications")
  public ResponseEntity<List<NotificationGetDto>> getMyWasteStatistics() {
    return new ResponseEntity<>(notificationService.gelAllCustomerNotifications().stream()
            .map(NotificationGetDto::toDto)
            .collect(Collectors.toList()), HttpStatus.OK);
  }


}
