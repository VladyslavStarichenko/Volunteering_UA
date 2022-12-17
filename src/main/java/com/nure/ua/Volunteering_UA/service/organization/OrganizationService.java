package com.nure.ua.Volunteering_UA.service.organization;

import com.nure.ua.Volunteering_UA.dto.organization.OrganizationGetDto;
import com.nure.ua.Volunteering_UA.dto.organization.OrganizationPageResponse;
import com.nure.ua.Volunteering_UA.model.Statistic;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import com.nure.ua.Volunteering_UA.service.security.service.UserServiceSCRT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class OrganizationService {

  private final OrganizationRepository organizationRepository;
  private final UserServiceSCRT userServiceSCRT;

  @Autowired
  public OrganizationService(OrganizationRepository organizationRepository, UserServiceSCRT userServiceSCRT) {
    this.organizationRepository = organizationRepository;
    this.userServiceSCRT = userServiceSCRT;
  }

  public OrganizationGetDto createOrganization(User admin, String organization_Name, Volunteering_Type volunteering_type) {
    Organization organization = new Organization(organization_Name, volunteering_type, admin);
    Organization save = organizationRepository.save(organization);
    OrganizationGetDto organizationGetDto = OrganizationGetDto.toDto(save);
    organizationGetDto.setStatistic(new Statistic());
    return organizationGetDto;
  }

  public Optional<OrganizationGetDto> getOrganizationByName(String name) {
    Optional<Organization> organizationGetDto = organizationRepository
            .getOrganizationByName(name);
    return organizationGetDto.map(OrganizationGetDto::toDto);
  }

  public List<OrganizationGetDto> getOrganizationByVolunteer(UUID userId) {
    return organizationRepository
            .getAllByVolunteer(userId)
            .stream()
            .map(OrganizationGetDto::toDto)
            .collect(Collectors.toList());
  }

  public List<OrganizationGetDto> getOrganizationByType(Volunteering_Type type) {
    return organizationRepository
            .getAllByVolunteering_type(type.ordinal())
            .stream()
            .map(OrganizationGetDto::toDto)
            .collect(Collectors.toList());
  }

  public OrganizationPageResponse getAllOrganizations(int pageNumber, int sizeOfPage) {
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage);
    return OrganizationPageResponse.toDto(organizationRepository.findAll(pageable), pageNumber);

  }

  public boolean delete() {
    Optional<Organization> organizationByOrg_admin = organizationRepository
            .findAllByOrg_admin(userServiceSCRT.getCurrentLoggedInUser().getId());
    if(organizationByOrg_admin.isPresent()){
      organizationRepository.delete(organizationByOrg_admin.get());
      return true;
    }
    return false;
  }

  public Optional<OrganizationGetDto> getMyOrganization() {
    Optional<Organization> organizationByOrg_admin = organizationRepository
            .findAllByOrg_admin(userServiceSCRT.getCurrentLoggedInUser().getId());
    return organizationByOrg_admin.map(OrganizationGetDto::toDto);
  }




}
