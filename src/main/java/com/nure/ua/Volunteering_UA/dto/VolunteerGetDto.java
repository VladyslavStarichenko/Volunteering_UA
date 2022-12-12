package com.nure.ua.Volunteering_UA.dto;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerGetDto {

  private String name;
  private String email;
  private Long id;
  private List<Pair<String, String>> organizations;

  public static VolunteerGetDto toDto(Volunteer volunteer){
    return new VolunteerGetDto(
            volunteer.getUser().getUserName(),
            volunteer.getUser().getEmail(),
            volunteer.getId(),
            volunteer.getVolunteering_area().stream()
                    .map(organization ->
                            new Pair<>(
                                    organization.getName(),
                                    organization.getVolunteering_type().toString()))
                    .collect(Collectors.toList()));
  }
}
