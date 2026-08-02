package com.foody.delivery.service;

import com.foody.delivery.database.repository.AddressRepository;
import com.foody.delivery.database.repository.UserRepository;
import com.foody.delivery.domain.address.Address;
import com.foody.delivery.domain.address.CreateAddressDTO;
import com.foody.delivery.domain.address.ResponseAddressDTO;
import com.foody.delivery.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public boolean addAddress(String user_id, CreateAddressDTO address) throws Exception {
        Optional<User> foundUser = this.userRepository.findById(user_id);

        if(foundUser.isEmpty()) {
            throw new Exception("User not found");
        }

        Address newAddress = new Address(address.city(), address.street(), address.number(), address.state(), foundUser.get());

        this.addressRepository.save(newAddress);

        return true;
    }

    public List<ResponseAddressDTO> getAddresses(String user_id) throws Exception {
        Optional<User> foundUser = this.userRepository.findById(user_id);

        if(foundUser.isEmpty()) {
            throw new Exception("User not found");
        }

        return this.addressRepository.findByUserId(user_id)
                .stream()
                .map(ResponseAddressDTO::new)
                .toList();
    }

    public boolean updateAddress(String address_id, CreateAddressDTO address) throws Exception {
        Optional<Address> foundAddress = this.addressRepository.findById(address_id);

        if(foundAddress.isEmpty()) {
            throw new Exception("Address not found");
        }

        Address updatedAddress = foundAddress.get();

        updatedAddress.update(address.city(), address.street(), address.state(), address.number());

        this.addressRepository.save(updatedAddress);

        return true;
    }

    public boolean deleteAddress(String address_id) throws Exception {
        Optional<Address> foundAddress = this.addressRepository.findById(address_id);

        if(foundAddress.isEmpty()) {
            throw new Exception("Address not found");
        }

        this.addressRepository.deleteById(address_id);

        return true;
    }

    public User getUser(String user_id) throws Exception {
        Optional<User> foundUser = this.userRepository.findById(user_id);

        if(foundUser.isEmpty()) {
            throw new Exception("User not found");
        }

        return foundUser.get();
    }
}
