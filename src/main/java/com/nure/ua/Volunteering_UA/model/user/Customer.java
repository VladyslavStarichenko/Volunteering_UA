package com.nure.ua.Volunteering_UA.model.user;

import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToMany
  @JoinTable(
          name = "subscriptions",
          joinColumns = @JoinColumn(name = "customer_id"),
          inverseJoinColumns = @JoinColumn(name = "organization_id")
  )
  private List<Organization> subscriptions;

  @OneToMany(mappedBy="customer")
  private List<Notification> notifications;

  @OneToMany(mappedBy = "customer")
  private List<Aid_Request> requests;

  public Customer(User user) {
    this.user = user;
    this.notifications = new ArrayList<>();
    this.subscriptions = new ArrayList<>();
    this.requests = new ArrayList<>();
  }

}
