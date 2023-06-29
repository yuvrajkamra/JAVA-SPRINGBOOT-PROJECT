package com.example.ewallet.demo1;

import com.example.ewallet.demo.UserIdentifier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber; // will be acting as a username in case of spring security
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String country;
    private String dob;

    @NotBlank
    private String identifierValue; // identifier is not null and .identifier.length()>0

    @NotNull
    private UserIdentifier userIdentifier;

    public  User to()
    {
        return User.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .password(this.password)
                .email(this.email)
                .country(this.country)
                .dob(this.dob)
                .userIdentifier(this.userIdentifier)
                .identifierValue(this.identifierValue)
                .build();
    }
}
