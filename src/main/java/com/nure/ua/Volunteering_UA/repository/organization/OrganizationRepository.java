package com.nure.ua.Volunteering_UA.repository.organization;

import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {

  Optional<Organization> findOrganizationByName(String name);

  Page<Organization> findAll(Pageable pageable);

  @Query(value = "SELECT * FROM organization o WHERE o.user_id == ?", nativeQuery = true)
  Optional<Organization> findOrganizationByOrg_admin(UUID org_admin);

}
