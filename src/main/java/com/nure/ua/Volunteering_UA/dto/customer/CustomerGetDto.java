package com.nure.ua.Volunteering_UA.dto.customer;

import com.nure.ua.Volunteering_UA.dto.request.Aid_Req_Dto;
import com.nure.ua.Volunteering_UA.model.user.Customer;

import java.util.List;

import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
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
