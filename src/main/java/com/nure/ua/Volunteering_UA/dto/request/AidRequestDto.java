package com.nure.ua.Volunteering_UA.dto.request;


import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Request_Status;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import lombok.Data;

@Data
public class AidRequestDto {
  private Long id;
  private String title;

  private String description;

  private int amount;

  private String organization;

  private String customer;

  private Request_Status requestStatus;

  private Volunteering_Type volunteering_type;

  public static AidRequestDto toDto(Aid_Request aid_request){
    AidRequestDto aidRequestDto = new AidRequestDto();
    aidRequestDto.setId(aid_request.getId());
    aidRequestDto.setRequestStatus(aid_request.getRequestStatus());
    aidRequestDto.setCustomer(aid_request.getCustomer().getUser().getUserName());
    aidRequestDto.setTitle(aid_request.getTitle());
    aidRequestDto.setAmount(aid_request.getAmount());
    aidRequestDto.setDescription(aid_request.getDescription());
    aidRequestDto.setOrganization(aid_request.getOrganization().getName());
    aidRequestDto.setVolunteering_type(aid_request.getVolunteering_type());
    return aidRequestDto;
  }

}
