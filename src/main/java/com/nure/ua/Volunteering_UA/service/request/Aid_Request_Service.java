package com.nure.ua.Volunteering_UA.service.request;

import com.nure.ua.Volunteering_UA.dto.request.AidRequestDto;
import com.nure.ua.Volunteering_UA.dto.request.AidRequestPageResponse;
import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Request_Status;
import com.nure.ua.Volunteering_UA.repository.request.Aid_Request_Repository;
import com.nure.ua.Volunteering_UA.service.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.random;


@Service
public class Aid_Request_Service {

  private final Aid_Request_Repository aid_request_repository;
  private final EmailSenderService emailSenderService;

  @Autowired
  public Aid_Request_Service(Aid_Request_Repository aid_request_repository, EmailSenderService emailSenderService) {
    this.aid_request_repository = aid_request_repository;
    this.emailSenderService = emailSenderService;
  }

  public AidRequestDto createRequest(Aid_Request aid_request) {
    return AidRequestDto.toDto(aid_request_repository.save(aid_request));
  }

  public AidRequestDto approve(Aid_Request aid_request) {
    aid_request.setRequestStatus(Request_Status.APPROVED);
    String confirmationCode = generateCode();
    int i = Integer.parseInt(confirmationCode);
    aid_request.setConformationCode(Integer.parseInt(confirmationCode));
    System.out.println(Integer.parseInt(confirmationCode));
    String SUBJECT = "Conformation of receiving aid code";
    aid_request_repository.update(i, aid_request.getId());
    aid_request_repository.save(aid_request);
    emailSenderService.sendMail(aid_request.getCustomer().getUser().getEmail(), SUBJECT, confirmationCode);
    return AidRequestDto.toDto(aid_request_repository.save(aid_request));
  }

  public AidRequestDto finish(Aid_Request aid_request, int conformationCode) {
    System.out.println();
    if (conformationCode == aid_request.getConformationCode()) {
      aid_request.setRequestStatus(Request_Status.DELIVERED);
    }
    return AidRequestDto.toDto(aid_request_repository.save(aid_request));
  }

  private String generateCode() {
    final int idLength = 6;
    return random(idLength, false, true);
  }

  public AidRequestPageResponse getAllRequestsByOrganization(int pageNumber, int sizeOfPage, String organizationName) {
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage);
    Page<Aid_Request> aid_requestByOrganization_name = aid_request_repository.getAid_RequestByOrganization_Name(pageable, organizationName);
    return AidRequestPageResponse.toDto(aid_requestByOrganization_name, pageNumber);
  }

  public AidRequestPageResponse getAllRequests(int pageNumber, int sizeOfPage) {
    Pageable pageable = PageRequest.of(pageNumber, sizeOfPage);
    Page<Aid_Request> allRequests = aid_request_repository.getAll(pageable);
    return AidRequestPageResponse.toDto(allRequests, pageNumber);
  }

  public List<AidRequestDto> getAllRequestsByCustomerId(Long customerId) {
    return aid_request_repository.getAid_RequestByCustomer_Id(customerId)
            .stream()
            .map(AidRequestDto::toDto)
            .collect(Collectors.toList());
  }

  public Optional<Aid_Request> getRequestById(Long requestId) {
    return aid_request_repository.findById(requestId);
  }

}
