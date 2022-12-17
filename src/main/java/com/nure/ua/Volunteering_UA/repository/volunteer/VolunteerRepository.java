package com.nure.ua.Volunteering_UA.repository.volunteer;


import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {
  Optional<Volunteer> findByUser(User user);
}
