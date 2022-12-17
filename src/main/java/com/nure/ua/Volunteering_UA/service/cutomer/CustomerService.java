package com.nure.ua.Volunteering_UA.service.cutomer;

import com.nure.ua.Volunteering_UA.dto.customer.CustomerGetDto;
import com.nure.ua.Volunteering_UA.dto.organization.OrganizationGetDto;
import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.repository.customer.CustomerRepository;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final UserServiceSCRT userServiceSCRT;
  private final OrganizationRepository organizationRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository, UserServiceSCRT userServiceSCRT, OrganizationRepository organizationRepository) {
    this.customerRepository = customerRepository;
    this.userServiceSCRT = userServiceSCRT;
    this.organizationRepository = organizationRepository;
  }

  public Optional<CustomerGetDto> myAccount() {
    return customerRepository
            .findByUser(userServiceSCRT.getCurrentLoggedInUser())
            .map(CustomerGetDto::toDto);
  }

  public void subscribeOrganization(String organizationName) {
    Optional<Customer> customerDb = customerRepository.findByUser(userServiceSCRT.getCurrentLoggedInUser());
    if (customerDb.isPresent()) {
      Optional<Organization> organizationDb = organizationRepository.getOrganizationByName(organizationName);
      if (organizationDb.isPresent()) {
        Customer customer = customerDb.get();
        customer.getSubscriptions().add(organizationDb.get());
        customerRepository.save(customer);
      }
    }
  }

  public void unSubscribeOrganization(String organizationName) {
    Optional<Customer> customerDb = customerRepository.findByUser(userServiceSCRT.getCurrentLoggedInUser());
    if (customerDb.isPresent()) {
      Optional<Organization> organizationDb = organizationRepository.getOrganizationByName(organizationName);
      if (organizationDb.isPresent()) {
        Customer customer = customerDb.get();
        List<Organization> subscriptions = customer.getSubscriptions();
        subscriptions.remove(organizationDb.get());
        customerRepository.save(customer);
      }
    }
  }
}
