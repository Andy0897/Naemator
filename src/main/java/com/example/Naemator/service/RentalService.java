package com.example.Naemator.service;

import com.example.Naemator.model.Listing;
import com.example.Naemator.model.Rental;
import com.example.Naemator.repository.ListingRepository;
import com.example.Naemator.repository.RentalRepository;
import com.example.Naemator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.time.LocalDate;

@Service
public class RentalService {
    RentalRepository rentalRepository;
    ListingRepository listingRepository;
    UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public String submitRentListing(Rental rental, BindingResult bindingResult, Long listingId, Principal principal, Model model) {
        if(bindingResult.hasFieldErrors("city") || bindingResult.hasFieldErrors("address")) {
            model.addAttribute("rental", rental);
            model.addAttribute("listingId", listingId);
            return "rental/rent";
        }
        Listing listing = listingRepository.findById(listingId).get();
        rental.setListing(listing);
        rental.setTotalPrice(sumPriceBetweenDates(rental.getStartDate(), rental.getEndDate(), listing.getPricePerDay()));
        rental.setRenter(userRepository.findByUsername(principal.getName()));
        rentalRepository.save(rental);
        return "redirect:/";
    }

    private double sumPriceBetweenDates(LocalDate startDate, LocalDate endDate, double pricePerDay) {
        double totalPrice = 0;
        for(LocalDate currentDate = startDate; currentDate.isBefore(endDate); currentDate.plusDays(1)) {
            totalPrice += pricePerDay;
        }
        return totalPrice;
    }
}