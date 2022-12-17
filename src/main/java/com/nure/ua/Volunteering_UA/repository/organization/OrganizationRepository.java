package com.nure.ua.Volunteering_UA.repository.organization;

import com.nure.ua.Volunteering_UA.model.Request_Status;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {

  @Query(value = " FROM Organization o WHERE o.name = :name")
  Optional<Organization> getOrganizationByName(@Param("name") String name);

  @Query(value = "SELECT * FROM organization o WHERE o.volunteering_type = ?", nativeQuery = true)
  List<Organization> getAllByVolunteering_type(int volunteering_type);

  @Query(value = "SELECT * FROM organization o WHERE o.id IN" +
          " (SELECT ov.organization_id FROM organization_volunteers ov WHERE ov.volunteer_id IN(" +
          "SELECT volunteer_id FROM ov WHERE user_id == ?)) ", nativeQuery = true)
  List<Organization> getAllByVolunteer(UUID userVolunteerId);

  Page<Organization> findAll(Pageable pageable);

  @Query(value = " FROM Organization o WHERE o.org_admin.id = :id")
  @Modifying

  Optional<Organization> findAllByOrg_admin(@Param("id") UUID id);

  boolean existsByName(String name);


}
