package com.example.Naemator.repository;

import com.example.Naemator.model.Listing;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Long> {
}