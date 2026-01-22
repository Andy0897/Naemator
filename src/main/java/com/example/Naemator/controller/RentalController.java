package com.example.Naemator.controller;

import com.example.Naemator.model.Rental;
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

@Controller
@RequestMapping("/rentals")
public class RentalController {
    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
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