package com.nure.ua.Volunteering_UA.dto;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.model.user.Volunteer;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGetDto {

  private String name;
  private String email;
  private Long id;
  private List<Aid_Req_Dto> aid_requests;

  public static CustomerGetDto toDto(Customer customer){
    return new CustomerGetDto(
            customer.getUser().getUserName(),
            customer.getUser().getEmail(),
            customer.getId(),
            customer.getRequests().stream()
                    .map(Aid_Req_Dto::toDto)
                    .collect(Collectors.toList()));
  }
}
