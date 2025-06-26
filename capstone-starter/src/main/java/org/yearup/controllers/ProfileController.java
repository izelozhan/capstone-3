package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("profile")

public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping("")
    public Profile getUserById(Principal principal) {
        //getUserId method is defined below => it gets the use that is logged in and return userID
        return profileDao.getByUserId(getUserId(principal));
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateProfile(Principal principal, @RequestBody Profile profile) {
        try {
            profileDao.update(getUserId(principal), profile);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    private int getUserId(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        return user.getId();
    }


}
