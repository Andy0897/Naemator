package com.example.Naemator.repository;

import com.example.Naemator.model.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    @Query(nativeQuery = true, value = "SELECT rental_id, end_date, start_date, total_price, listing_listing_id, renter_user_id, r.city, address FROM `rentals` r\n" +
            "JOIN listings l ON l.listing_id = r.listing_listing_id\n" +
            "WHERE l.owner_user_id = :ownerId;")
    public List<Rental> findAllByListingOwner(@Param("ownerId") Long ownerId);

    @Query(nativeQuery = true, value = "SELECT rental_id, end_date, start_date, total_price, listing_listing_id, renter_user_id, r.city, address FROM `rentals` r\n" +
            "WHERE r.renter_user_id = :renterId;")
    public List<Rental> findAllByRenter(@Param("renterId") Long renterId);
}