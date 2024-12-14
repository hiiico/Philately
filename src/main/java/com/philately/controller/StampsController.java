package com.philately.controller;


import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.philately.model.dto.AddStampDTO;

@RequestMapping("/stamps")
public interface StampsController {

    @GetMapping("/add-stamps")
    String addStamp();

    @PostMapping("/add-stamps")
    String addStamp(@Valid AddStampDTO addStampDTO, BindingResult result, RedirectAttributes redirectAttributes);

    @GetMapping("/add/{id}")
    String addToWishList(@PathVariable Long id);

    @GetMapping("/remove/{id}")
    String removeStamp(@PathVariable Long id);

    @GetMapping("/buy-stamps")
    String buyStamps(Long id);

    @GetMapping("/remove-from-wishlist/{id}")
    String removeFromWishListById(@PathVariable Long id);

}
