package com.nure.ua.Volunteering_UA.repository.customer;

import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  Optional<Customer> findByUser(User user);
}
