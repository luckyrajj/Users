package com.example.ums.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
