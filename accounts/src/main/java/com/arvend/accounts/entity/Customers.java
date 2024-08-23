package com.arvend.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers", indexes={
        @Index(name = "idx_mobile_number", columnList = "mobile_number"),
        @Index(name = "idx_is_active", columnList = "is_active")
})
public class Customers {

    public Customers(String name, String email, String mobileNumber, String createdBy, int isActive)
    {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.createdBy = createdBy;
        this.isActive = isActive;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name="mobile_number",nullable = false)
    private String mobileNumber;


    @Column(name="created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="created_by",nullable = false)
    private String createdBy;


    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="is_active")
    private int isActive;

    @OneToMany(mappedBy = "customer")
    public List<Accounts> account;

    @PrePersist
    protected void onCreate()
    {
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
        isActive = 1;
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt=LocalDateTime.now();
    }

}
