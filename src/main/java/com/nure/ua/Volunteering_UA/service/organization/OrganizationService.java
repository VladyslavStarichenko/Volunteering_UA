package com.nure.ua.Volunteering_UA.service.organization;

import com.nure.ua.Volunteering_UA.dto.OrganizationGetDto;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import com.nure.ua.Volunteering_UA.repository.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


//TODO
// update org
// delete org
// get info
// get all org

@Service
public class OrganizationService {

  private final OrganizationRepository organizationRepository;

  @Autowired
  public OrganizationService(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  public OrganizationGetDto createOrganization(User admin, String organization_Name, Volunteering_Type volunteering_type){
    Organization organization = new Organization(organization_Name,volunteering_type,admin);
    return OrganizationGetDto.toDto(organizationRepository.save(organization));
  }

  public Optional<OrganizationGetDto> getOrganizationByName(String name){
    return organizationRepository
            .findOrganizationByName(name)
            .map(OrganizationGetDto::toDto)
            .or(() -> Optional.of(new OrganizationGetDto()));
  }

  public Page<Organization> getAllOrganizations(int pageNumber, int sizeOfPage){
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage);
    return organizationRepository.findAll(pageable);
  }

  public void delete(User admin){
    Optional<Organization> organizationByOrg_admin = organizationRepository.findOrganizationByOrg_admin(admin.getId());
    organizationByOrg_admin.ifPresent(organizationRepository::delete);
  }

}
