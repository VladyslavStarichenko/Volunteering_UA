package com.nure.ua.Volunteering_UA.repository.notification;

import java.util.List;
import com.nure.ua.Volunteering_UA.model.Notification;
import com.nure.ua.Volunteering_UA.model.user.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

  List<Notification> getNotificationByCustomer(Customer customer);
}
