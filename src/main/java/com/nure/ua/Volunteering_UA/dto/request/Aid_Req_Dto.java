package com.nure.ua.Volunteering_UA.dto.request;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Request_Status;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import lombok.Value;

@Value
public class Aid_Req_Dto {

  String title;
  String description;
  Volunteering_Type volunteering_type;
  Request_Status requestStatus;

  public static Aid_Req_Dto toDto(Aid_Request aid_request) {
    return new Aid_Req_Dto(
            aid_request.getTitle(),
            aid_request.getDescription(),
            aid_request.getVolunteering_type(),
            aid_request.getRequestStatus()
            );
  }
}
