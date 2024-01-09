package com.personal.digital.commservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmailRequestDTO {
  private String accountno;
  private String data;
  private String email;
  private String name;
}
