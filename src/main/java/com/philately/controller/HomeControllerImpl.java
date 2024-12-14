package com.philately.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.philately.model.entity.Stamp;
import com.philately.model.entity.User;
import com.philately.service.StampsServiceImpl;
import com.philately.service.UserServiceImpl;
import com.philately.util.LoggedUser;

import java.util.List;
import java.util.Set;

@Controller
public class HomeControllerImpl implements HomeController {

    private final LoggedUser loggedUser;
    private final UserServiceImpl userService;

    private final StampsServiceImpl stampsService;

    public HomeControllerImpl(LoggedUser loggedUser,
                              UserServiceImpl userService, StampsServiceImpl stampsService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.stampsService = stampsService;
    }

    @Override
    public String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @Override
    public String home(Model model) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }


        User user = userService.findUserById(loggedUser.getId()).orElse(null);
        model.addAttribute("currentUserInfo", user);

        assert user != null;
        List<Stamp> allStampsByUser = this.stampsService.getAllStampsByUser((user.getId()));
        List<Stamp> allStamps = this.stampsService.getAllStamps(user);
        Set<Stamp> userWishedStamps = this.stampsService.getWishedStampsByUser(user.getId());
        Set<Stamp> boughtStampsByUser = this.stampsService.getBoughtStampsByUser(user.getId());

        model.addAttribute("allStampsByUser", allStampsByUser);
        model.addAttribute("allStamps", allStamps);
        model.addAttribute("userWishedStamps", userWishedStamps);
        model.addAttribute("boughtStampsByUser", boughtStampsByUser);

        return "home";
    }

}
