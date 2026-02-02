package com.example.Naemator.controller;

import com.example.Naemator.model.Listing;
import com.example.Naemator.model.User;
import com.example.Naemator.repository.CategoryRepository;
import com.example.Naemator.repository.ListingRepository;
import com.example.Naemator.repository.UserRepository;
import com.example.Naemator.service.ListingService;
import com.example.Naemator.util.ImageEncoder;
import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/listings")
public class ListingController {
    ListingService listingService;
    ListingRepository listingRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public ListingController(ListingService listingService, ListingRepository listingRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.listingService = listingService;
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getShowListings(Model model) {
        model.addAttribute("listings", listingRepository.findAllAvailable());
        model.addAttribute("encoder", new ImageEncoder());
        return "listing/listings";
    }

    @GetMapping("/my-listings")
    public String getShowListings(Model model, Principal principal) {
        User owner = userRepository.findByUsername(principal.getName());
        model.addAttribute("listings", listingRepository.findAllByOwnerId(owner.getId()));
        model.addAttribute("encoder", new ImageEncoder());
        return "listing/my-listings";
    }

    @GetMapping("/{listingId}")
    public String getShowSingleListing(@PathVariable("listingId") Long listingId, Principal principal, Model model) {
        Listing listing = listingRepository.findById(listingId).get();
        model.addAttribute("listing", listing);
        model.addAttribute("isMine", listing.getOwner().getUsername().equals(principal.getName()));
        model.addAttribute("encoder", new ImageEncoder());
        return "listing/listing-details";
    }

    @GetMapping("/create")
    public String getCreateListing(Model model) {
        Listing listing = new Listing();
        model.addAttribute("listing", listing);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("hasUploadError", false);
        model.addAttribute("areImagesSelected", true);
        return "listing/create";
    }

    @PostMapping("/submit")
    public String getSubmitCreateListing(@Valid Listing listing, BindingResult bindingResult, @RequestParam(name = "images") MultipartFile[] images, Principal principal, Model model) {
        return listingService.submitCreateListing(listing, bindingResult, images, principal, model);
    }

    @GetMapping("/manage-deleted-listings")
    public String getManageDeletedListings(Model model) {
        List<Listing> deletedListings = listingRepository.findAllDeleted();
        model.addAttribute("deletedListings", deletedListings);
        model.addAttribute("encoder", new ImageEncoder());
        return "listing/deleted-listings";
    }

    @PostMapping("/submit-delete-listing/{listingId}")
    public String getSubmitDeleteListing(@PathVariable("listingId") Long listingId, Principal principal) {
        return listingService.submitDeleteListing(listingId, principal);
    }

    @PostMapping("/submit-restore-listing/{listingId}")
    public String getSubmitRestoreListing(@PathVariable("listingId") Long listingId) {
        return listingService.submitRestoreListing(listingId);
    }
}