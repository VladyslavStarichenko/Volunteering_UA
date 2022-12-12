package com.nure.ua.Volunteering_UA.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.nure.ua.Volunteering_UA.model.Statistic;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationGetDto {

  private String name;
  private String type;
  private String admin;
  private List<VolunteerGetDto> volunteers;
  private Statistic statistic;

  public static OrganizationGetDto toDto(Organization organization){
    return new OrganizationGetDto(
            organization.getName(),
            organization.getVolunteering_type().toString(),
            organization.getOrg_admin().getUserName(),
            organization.getVolunteers()
                    .stream()
                    .map(VolunteerGetDto::toDto)
                    .collect(Collectors.toList()),
            new Statistic()
            );
  }

}
