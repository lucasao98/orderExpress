package com.foody.delivery.controller;

import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.address.CreateAddressDTO;
import com.foody.delivery.domain.address.ResponseAddressDTO;
import com.foody.delivery.domain.user.User;
import com.foody.delivery.domain.user.UserInfoDTO;
import com.foody.delivery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/address/{id}")
    public ResponseEntity addAddress(@PathVariable("id") String user_id, @RequestBody CreateAddressDTO address) {
        try {
            this.userService.addAddress(user_id, address);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/address/{id}")
    public ResponseEntity getUserAddresses(@PathVariable("id") String user_id) {
        try {
            List<ResponseAddressDTO> addresses = this.userService.getAddresses(user_id);

            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable("id") String user_id) {
        try {
            User user = this.userService.getUser(user_id);

            return new ResponseEntity<>(new UserInfoDTO(user.getName(), user.getEmail(), user.getRole()), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/address/{id}")
    public ResponseEntity updateUserAddress(@PathVariable("id") String address_id, @RequestBody CreateAddressDTO address) {
        try {
            this.userService.updateAddress(address_id, address);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity deleteAddress(@PathVariable("id") String address_id) {
        try {
            this.userService.deleteAddress(address_id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
