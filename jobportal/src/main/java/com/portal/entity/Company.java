package com.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyId;
    private String name;
    private String email;
    private String password;
    private String description;
    @OneToMany(mappedBy = "company",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Location> locations;
    @OneToMany(mappedBy = "company", cascade =  CascadeType.ALL)
    private List<Jobs> jobsList;
    @ManyToOne
    private Roles role;
    @OneToOne(cascade=CascadeType.ALL)
    private ProfileImage profileImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<CompanyCategory> categoryList;
    private long otp;
    private LocalTime currentTimeOtp;
    boolean isOtpActive;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
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
