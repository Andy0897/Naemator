package com.example.Naemator.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
    @Column(name = "rental_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Listing listing;

    private User renter;

    private LocalDate startDate;

    private LocalDate endDate;

    private double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}