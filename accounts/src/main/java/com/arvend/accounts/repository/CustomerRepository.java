package com.arvend.accounts.repository;

import com.arvend.accounts.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Integer> {

    /**
     * *********
     * How Spring Data JPA knows we are trying to query based upon Mobile Number
     * Based on Naming Convention
     * findBy => When using findBy means we are trying to run SELECT query
     * MobileNumber => This is the column in the table
     * --- The field MobileNumber need to match with what we mention on the Customer Entity class ==> private String mobileNumber;
     * In Summary, we Spring Data JPA knows to select based on MobileNumber
     * ==> We call this concept as derived named method because based upon the method names, my spring data JPA
     * ====> framework is going to prepare the SQL queries and run the same onto the database.
     *
     */
    //Optional<Customers> findByMobileNumber(String mobileNumber);

    /**
     * ************ JPQL QUERIES
     */

    @Query("SELECT c from Customers c where c.mobileNumber = :mobileNumber AND c.isActive = 1")
    Optional<Customers> findByMobileNumberAndIsActive(@Param("mobileNumber") String mobileNumber);

    @Query("SELECT c from Customers c where c.isActive = 1")
    List<Customers> findAllActiveCustomers();

}
