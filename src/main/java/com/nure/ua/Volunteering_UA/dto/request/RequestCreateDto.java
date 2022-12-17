package com.nure.ua.Volunteering_UA.dto.request;

import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import lombok.Data;

@Data
public class RequestCreateDto {

  private String title;
  private String description;
  private int amount;
  private Volunteering_Type volunteering_type;
  private String organizationName;


}
