package com.luv2tech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @NaturalId
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @JsonIgnore
    @Column(nullable = false, name = "account_non_expired")
    private boolean accountNonExpired = false;

    @JsonIgnore
    @Column(nullable = false, name = "account_non_locked")
    private boolean accountNonLocked = false;

    @JsonIgnore
    @Column(nullable = false, name = "credentials_non_expired")
    private boolean credentialsNonExpired = false;

    @JsonIgnore
    @Column(nullable = false, name = "enabled")
    private boolean enabled = false;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;

}
