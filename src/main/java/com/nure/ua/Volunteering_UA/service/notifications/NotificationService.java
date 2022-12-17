package com.nure.ua.Volunteering_UA.service.notifications;


import com.nure.ua.Volunteering_UA.model.Notification;
import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.repository.customer.CustomerRepository;
import com.nure.ua.Volunteering_UA.repository.notification.NotificationRepository;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final CustomerRepository customerRepository;
  private final UserServiceSCRT userServiceSCRT;

  @Autowired
  public NotificationService(NotificationRepository notificationRepository, CustomerRepository customerRepository, UserServiceSCRT userServiceSCRT) {
    this.notificationRepository = notificationRepository;
    this.customerRepository = customerRepository;
    this.userServiceSCRT = userServiceSCRT;
  }

  public List<Notification> gelAllCustomerNotifications() {
    Optional<Customer> customer = customerRepository.findByUser(userServiceSCRT.getCurrentLoggedInUser());
    if(customer.isPresent()){
      return notificationRepository.getNotificationByCustomer(customer.get());
    }
    return new ArrayList<>();
  }

  public void createNotification(Notification notification) {
     notificationRepository.save(notification);
  }
}
