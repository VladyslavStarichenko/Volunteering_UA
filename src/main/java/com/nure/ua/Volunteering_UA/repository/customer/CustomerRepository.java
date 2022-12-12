package com.nure.ua.Volunteering_UA.repository.customer;

import com.nure.ua.Volunteering_UA.model.user.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
