package com.arvend.accounts.repository;

import com.arvend.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    // Using Native Queries
    // If you need to write a native SQL query, you can use the nativeQuery attribute of the @Query annotation:

    @Query(value = "SELECT * FROM accounts WHERE customer_id = :customerId AND is_active = 1", nativeQuery = true)
    List<Accounts> findByCustomerCustomerId(@Param("customerId") int customerId);

}
