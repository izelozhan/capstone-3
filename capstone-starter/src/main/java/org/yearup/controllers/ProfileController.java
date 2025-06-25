package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Category;
import org.yearup.models.Profile;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")

public class ProfileController {
    private ProfileDao profileDao;

    @Autowired
    public ProfileController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Profile getUserById(@PathVariable int id) {
        return profileDao.getByUserId(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProfile(@PathVariable int id, @RequestBody Profile profile) {
        try {
            profileDao.update(id, profile);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


}
