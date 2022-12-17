package com.nure.ua.Volunteering_UA.model;


import lombok.Data;

@Data
public class Statistic {

  private int requestsCount;
  private String deliveredCount;
  private String inVerificationRequestsCount;
  private String approvedRequestsCount;

  public Statistic() {
    this.requestsCount = 0;
    this.deliveredCount = 0 + "%";
    this.inVerificationRequestsCount = 0 + "%";
    this.approvedRequestsCount = 0 + "%";


  }
}
