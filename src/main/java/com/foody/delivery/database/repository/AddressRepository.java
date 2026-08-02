package com.foody.delivery.database.repository;

import com.foody.delivery.domain.address.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String> {
    @Query("SELECT a FROM address a WHERE a.user.user_id = :userId")
    List<Address> findByUserId(@Param("userId") String userId);
}
