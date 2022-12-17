package com.nure.ua.Volunteering_UA.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class AidRequestPageResponse {


  private List<AidRequestDto> requests;
  private int pageNumber;
  private int pageSize;
  private int totalElements;
  private int totalPages;


  public static AidRequestPageResponse toDto(Page<Aid_Request> requests, int pageNumber) {
    AidRequestPageResponse aidRequestPageResponse = new AidRequestPageResponse();
    aidRequestPageResponse.setRequests(requests.getContent()
            .stream()
            .map(AidRequestDto::toDto)
            .collect(Collectors.toList()));
    aidRequestPageResponse.setPageNumber(pageNumber);
    aidRequestPageResponse.setPageSize(requests.getSize());
    aidRequestPageResponse.setTotalPages(requests.getTotalPages());
    aidRequestPageResponse.setTotalElements(requests.getNumberOfElements());
    return aidRequestPageResponse;
  }

}
