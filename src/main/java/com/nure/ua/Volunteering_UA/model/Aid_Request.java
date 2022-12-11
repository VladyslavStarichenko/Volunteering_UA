package com.nure.ua.Volunteering_UA.model;

import com.nure.ua.Volunteering_UA.model.user.Customer;
import com.nure.ua.Volunteering_UA.model.user.Organization;
import com.nure.ua.Volunteering_UA.model.user.Volunteering_Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Aid_Request {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="org_id", nullable=false)
  private Organization organization;

  @ManyToOne
  @JoinColumn(name = "customer_id",nullable = false)
  private Customer customer;

  private Volunteering_Type volunteering_type;

  private Request_Status requestStatus;



}
