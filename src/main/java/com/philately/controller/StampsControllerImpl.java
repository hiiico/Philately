package com.philately.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.philately.model.dto.AddStampDTO;
import com.philately.model.entity.enums.PaperEnum;
import com.philately.service.StampsServiceImpl;
import com.philately.service.UserServiceImpl;
import com.philately.util.LoggedUser;

@Controller
public class StampsControllerImpl implements StampsController {

    private final LoggedUser loggedUser;

    private final StampsServiceImpl stampsService;

    private final UserServiceImpl userService;

    public StampsControllerImpl(LoggedUser loggedUser, StampsServiceImpl stampsService, UserServiceImpl userService) {
        this.loggedUser = loggedUser;
        this.stampsService = stampsService;
        this.userService = userService;
    }

    @ModelAttribute("addStampDTO")
    public AddStampDTO init() {
        return new AddStampDTO();
    }

    @ModelAttribute("papers")
    public PaperEnum[] getPapers() {

        return PaperEnum.values();
    }

    @Override
    public String addStamp() {
        if (!loggedUser.isLogged()) {
            return "redirect:/users/login";
        }

        return "add-stamp";
    }

    @Override
    public String addStamp(AddStampDTO addStampDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addStampDTO", addStampDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addStampDTO", result);

            return "redirect:/stamps/add-stamps";
        }

        this.stampsService.addStamp(addStampDTO,loggedUser.getId());
        return "redirect:/home";
    }

    @Override
    public String addToWishList(Long id) {
        stampsService.addToWishList(id, loggedUser.getId());
        return "redirect:/home";
    }

    @Override
    public String removeStamp(Long id) {
        return null;
    }

    @Override
    public String buyStamps(Long id) {
        stampsService.buyStamps(loggedUser.getId());
        return "redirect:/home";
    }

    @Override
    public String removeFromWishListById(Long id) {
        stampsService.removeFromWishListById(id, loggedUser.getId());
        return "redirect:/home";
    }
}
