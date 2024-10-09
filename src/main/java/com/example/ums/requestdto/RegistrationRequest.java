package com.example.ums.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
