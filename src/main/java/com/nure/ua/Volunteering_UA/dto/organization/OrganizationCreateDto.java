package com.nure.ua.Volunteering_UA.dto.organization;


import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrganizationCreateDto {

   private String name;
   private Volunteering_Type volunteering_type;
}
