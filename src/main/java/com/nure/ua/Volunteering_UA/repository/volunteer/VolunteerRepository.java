package com.nure.ua.Volunteering_UA.repository.volunteer;


import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {
}
