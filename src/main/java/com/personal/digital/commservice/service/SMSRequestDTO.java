package com.personal.digital.commservice.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SMSRequestDTO {

  private String toNumber;
  private String accountNumber;
}
