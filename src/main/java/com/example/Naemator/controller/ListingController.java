package com.example.Naemator.controller;

import com.example.Naemator.model.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/listings")
public class ListingController {
    @GetMapping("/create")
    public String getCreateListing(Model model) {
        Listing listing = new Listing();
        model.addAttribute("listing", listing);
        return "listing/create";
    }
}