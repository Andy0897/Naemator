package com.example.Naemator.service;

import com.example.Naemator.model.Listing;
import com.example.Naemator.model.ListingStatus;
import com.example.Naemator.model.User;
import com.example.Naemator.repository.CategoryRepository;
import com.example.Naemator.repository.ListingRepository;
import com.example.Naemator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService {
    ListingRepository listingRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public String submitCreateListing(Listing listing, BindingResult bindingResult, MultipartFile[] images, Principal principal, Model model) {
        List<byte[]> imageList = new ArrayList<>();
        boolean areImagesSelected = false;
        boolean hasUploadError = false;
        try {
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    imageList.add(file.getBytes());
                    areImagesSelected = true;
                }
            }
        } catch (Exception e) {
            hasUploadError = true;
        }
        if (bindingResult.hasFieldErrors("title") || bindingResult.hasFieldErrors("description") || bindingResult.hasFieldErrors("pricePerDay") || bindingResult.hasFieldErrors("city") || hasUploadError || !areImagesSelected) {
            model.addAttribute("listing", listing);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("hasUploadError", hasUploadError);
            model.addAttribute("areImagesSelected", areImagesSelected);
            return "listing/create";
        }
        User user = userRepository.findByUsername(principal.getName());
        listing.setImages(imageList);
        listing.setListingStatus(ListingStatus.AVAILABLE);
        listing.setOwner(user);
        listing.setCreatedAt(LocalDate.now());
        listingRepository.save(listing);
        return "redirect:/listings";
    }

    public String submitDeleteListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId).get();
        listing.setListingStatus(ListingStatus.DELETED);
        listingRepository.save(listing);
        return "redirect:/listings";
    }

    public String submitRestoreListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId).get();
        listing.setListingStatus(ListingStatus.AVAILABLE);
        listingRepository.save(listing);
        return "redirect:/listings/manage-deleted-listings";
    }
}