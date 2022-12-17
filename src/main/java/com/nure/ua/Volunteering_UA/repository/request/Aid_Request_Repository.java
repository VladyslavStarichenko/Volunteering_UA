package com.nure.ua.Volunteering_UA.repository.request;

import java.util.List;
import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Request_Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface Aid_Request_Repository extends PagingAndSortingRepository<Aid_Request, Long> {

  Page<Aid_Request> getAid_RequestByOrganization_Name(Pageable pageable,String organizationName);

  @Query(value = "SELECT * FROM request", nativeQuery = true)
  Page<Aid_Request> getAll(Pageable pageable);

  List<Aid_Request> getAid_RequestByCustomer_Id(Long customerId);

  @Modifying
  @Transactional
  @Query(value = "update request set code=? where id=?", nativeQuery = true)
  void update(int code, Long id);
}
