package com.example.Naemator.controller;

import com.example.Naemator.model.Rental;
import com.example.Naemator.model.User;
import com.example.Naemator.repository.RentalRepository;
import com.example.Naemator.repository.UserRepository;
import com.example.Naemator.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {
    RentalService rentalService;
    RentalRepository rentalRepository;
    UserRepository userRepository;

    public RentalController(RentalService rentalService, RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/my-rents")
    public String getMyRents(Principal principal, Model model) {
        User renter = userRepository.findByUsername(principal.getName());
        List<Rental> rentals = rentalRepository.findAllByRenter(renter.getId());
        model.addAttribute("rentals", rentals);
        return "rental/my-listings-rents";
    }

    @GetMapping("/my-listings-rents")
    public String getMyListingsRents(Principal principal, Model model) {
        User owner = userRepository.findByUsername(principal.getName());
        List<Rental> rentals = rentalRepository.findAllByListingOwner(owner.getId());
        model.addAttribute("rentals", rentals);
        return "rental/my-listings-rents";
    }

    @GetMapping("/rent/{listingId}")
    public String getRentListing(@PathVariable("listingId") Long listingId, Model model) {
        Rental rental = new Rental();
        model.addAttribute("rental", rental);
        model.addAttribute("listingId", listingId);
        return "rental/rent";
    }

    @PostMapping("/submit-rent/{listingId}")
    public String getSubmitRentListing(@Valid Rental rental, BindingResult bindingResult, @PathVariable("listingId") Long listingId, Principal principal, Model model) {
        return rentalService.submitRentListing(rental, bindingResult, listingId, principal, model);
    }
}