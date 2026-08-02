package com.foody.delivery.domain.address;

public record ResponseAddressDTO(
        String city,
        String street,
        Integer number,
        String state
) {
    public ResponseAddressDTO(Address address) {
        this(address.getCity(), address.getStreet(), address.getNumber(), address.getState());
    }
}