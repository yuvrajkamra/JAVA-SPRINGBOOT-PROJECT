package com.example.ewallet.demo1;

import com.example.ewallet.demo.UserIdentifier;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Serializable -> In order to store it in redis.//
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    private String name;

    @Column(unique=true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String password;
    private String country;
    private String dob;

    private String authorities;

    @Column(unique = true)
    private String identifierValue; // identifier is not null and .identifier.length()>0


    @Enumerated(value=EnumType.STRING) // IT STORES ENUM AS STRING IN DB
    private UserIdentifier userIdentifier;


    @CreationTimestamp
    private Date createdOn;


    @UpdateTimestamp
    private Date updatedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] allAuthorities =this.authorities.split(UserConstants.AUTHORITIES_DELIMITER);
        return Arrays.stream(allAuthorities)
                .map(x->new SimpleGrantedAuthority(x))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
