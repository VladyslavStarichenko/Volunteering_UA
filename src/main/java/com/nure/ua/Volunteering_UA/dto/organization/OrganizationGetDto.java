package com.nure.ua.Volunteering_UA.dto.organization;

import java.util.List;
import java.util.stream.Collectors;

import com.nure.ua.Volunteering_UA.dto.volunteer.VolunteerGetDto;
import com.nure.ua.Volunteering_UA.model.Statistic;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationGetDto {

  private String name;
  private String type;
  private String admin;
  private List<VolunteerGetDto> volunteers;
  private Statistic statistic;

  public OrganizationGetDto(String name, String type, String admin, List<VolunteerGetDto> volunteers) {
    this.name = name;
    this.type = type;
    this.admin = admin;
    this.volunteers = volunteers;
  }

  public static OrganizationGetDto toDto(Organization organization) {
    OrganizationGetDto organizationGetDto = new OrganizationGetDto(
            organization.getName(),
            organization.getVolunteering_type().toString(),
            organization.getOrg_admin().getUserName(),
            organization.getVolunteers()
                    .stream()
                    .map(VolunteerGetDto::toDto)
                    .collect(Collectors.toList())
    );
    organizationGetDto.setStatistic(organization.getOrganizationStatistics());
    return organizationGetDto;
  }

}
