package com.personal.digital.commservice.dto;

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
