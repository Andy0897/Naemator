package com.example.Naemator.service;

import com.example.Naemator.model.Listing;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService {
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
        if(bindingResult.hasFieldErrors("title") || bindingResult.hasFieldErrors("description") || bindingResult.hasFieldErrors("pricePerDay") || bindingResult.hasFieldErrors("city") || hasUploadError || !areImagesSelected) {

        }
    }
}
