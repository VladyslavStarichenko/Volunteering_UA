package com.nure.ua.Volunteering_UA.dto;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import lombok.Value;

@Value
public class Aid_Req_Dto {

  String title;
  String description;


  public static Aid_Req_Dto toDto(Aid_Request aid_request) {
    return new Aid_Req_Dto(aid_request.getTitle(), aid_request.getDescription());
  }
}
