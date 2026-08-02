package com.foody.delivery.domain.address;

public record ResponseAddressDTO(
        String addressId,
        String city,
        String street,
        Integer number,
        String state
) {
    public ResponseAddressDTO(Address address) {
        this(
                address.getAddress_id(),
                address.getCity(),
                address.getStreet(),
                address.getNumber(),
                address.getState()
        );
    }
}