package com.nure.ua.Volunteering_UA.service.volunteer;


import com.nure.ua.Volunteering_UA.dto.volunteer.VolunteerGetDto;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import com.nure.ua.Volunteering_UA.repository.volunteer.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VolunteerService {

  private final VolunteerRepository volunteerRepository;

  @Autowired
  public VolunteerService(VolunteerRepository volunteerRepository) {
    this.volunteerRepository = volunteerRepository;
  }

  public void registerInOrganization(Organization organization, User user) {
    Optional<Volunteer> volunteerDb = volunteerRepository.findByUser(user);
    volunteerDb.ifPresent(volunteer ->{
      if(!volunteer.getVolunteering_area().contains(organization)){
        volunteer.getVolunteering_area().add(organization);
      }
    }) ;
    volunteerDb.ifPresent(volunteerRepository::save);

  }

  public void unRegisterInOrganization(Organization organization, User user) {
    Optional<Volunteer> volunteerDb = volunteerRepository.findByUser(user);
    volunteerDb.ifPresent(volunteer -> volunteer.getVolunteering_area().remove(organization));
    volunteerDb.ifPresent(volunteerRepository::save);
  }

  public Optional<VolunteerGetDto> getMyAccount(User user) {
    return volunteerRepository.findByUser(user).map(VolunteerGetDto::toDto);
  }

}
