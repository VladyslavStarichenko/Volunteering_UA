package com.nure.ua.Volunteering_UA.model.user;


import com.nure.ua.Volunteering_UA.model.Aid_Request;
import com.nure.ua.Volunteering_UA.model.BaseEntity;
import com.nure.ua.Volunteering_UA.model.Statistic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Volunteering_Type volunteering_type;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User org_admin;

  @ManyToMany(mappedBy = "subscriptions")
  private List<Customer> subscribers;

  @ManyToMany(mappedBy = "volunteering_area")
  private List<Volunteer> volunteers;

  @OneToMany(mappedBy = "organization")
  private List<Aid_Request> requests;

  public Organization(String name, Volunteering_Type volunteering_type, User org_admin) {
    this.name = name;
    this.volunteering_type = volunteering_type;
    this.org_admin = org_admin;
    this.volunteers = new ArrayList<>();
    this.subscribers = new ArrayList<>();
    this.requests = new ArrayList<>();

  }

  //  @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "statistic_id", referencedColumnName = "id")
//  private Statistic statistic;
}
