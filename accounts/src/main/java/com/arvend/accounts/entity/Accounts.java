package com.arvend.accounts.entity;

import com.arvend.accounts.constant.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "accounts")
@Table(name="accounts", indexes = {
        @Index(name = "idx_is_active", columnList = "is_active")
})
public class Accounts {

    @Id
    @Column(name="account_number")
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="account_type",nullable = false)
    private AccountType accountType;

    @Column(name="branch_address",nullable = false)
    private String branchAddress;

    @CreatedDate
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name="created_by",nullable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name = "is_active")
    private int isActive;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    public Customers customer;

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
