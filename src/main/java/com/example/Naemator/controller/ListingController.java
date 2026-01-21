package com.example.Naemator.controller;

import com.example.Naemator.model.Listing;
import com.example.Naemator.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/listings")
public class ListingController {
    ListingService listingService;

    @GetMapping("/create")
    public String getCreateListing(Model model) {
        Listing listing = new Listing();
        model.addAttribute("listing", listing);
        return "listing/create";
    }

    @PostMapping("/submit")
    public String getSubmitCreateListing(@Valid Listing listing, BindingResult bindingResult, @RequestParam(name = "images") MultipartFile[] images, Principal principal, Model model) {

    }
}