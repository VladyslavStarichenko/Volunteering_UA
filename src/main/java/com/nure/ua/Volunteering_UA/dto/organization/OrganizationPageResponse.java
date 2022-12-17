package com.nure.ua.Volunteering_UA.dto.organization;

import com.nure.ua.Volunteering_UA.model.Statistic;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrganizationPageResponse {

  List<OrganizationGetDto> organizations;
  private int pageNumber;
  private int pageSize;
  private int totalElements;
  private int totalPages;

  public static OrganizationPageResponse toDto(Page<Organization> organizations, int pageNumber){
    OrganizationPageResponse organizationPageResponse = new OrganizationPageResponse();
    organizationPageResponse.setOrganizations(organizations.getContent()
            .stream()
            .map(OrganizationGetDto::toDto)
            .collect(Collectors.toList()));
    organizationPageResponse.setPageSize(organizations.getContent().size());
    organizationPageResponse.setPageNumber(pageNumber);
    organizationPageResponse.setTotalPages(organizations.getTotalPages());
    organizationPageResponse.setTotalElements(organizations.getNumberOfElements());
    return organizationPageResponse;
  }

}
