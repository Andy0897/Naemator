package com.example.Naemator.repository;

import com.example.Naemator.model.Listing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ListingRepository extends CrudRepository<Listing, Long> {
    @Query(nativeQuery = true, value = "SELECT listing_id, city, created_at, description, listing_status, price_per_day, title, category_category_id, owner_user_id FROM listings WHERE listing_status = 'AVAILABLE'")
    public List<Listing> findAllAvailable();

    @Query(nativeQuery = true, value = "SELECT listing_id, city, created_at, description, listing_status, price_per_day, title, category_category_id, owner_user_id FROM listings WHERE listing_status = 'DELETED'")
    public List<Listing> findAllDeleted();
}