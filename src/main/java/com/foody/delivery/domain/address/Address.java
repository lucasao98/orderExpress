package com.foody.delivery.domain.address;

import com.foody.delivery.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name="address")
@Entity(name="address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String address_id;

    private String city;
    private String street;
    private Integer number;
    private String state;

    public Address(String city, String street, Integer number, String state, User user) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.state = state;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(String city, String street, String state, Integer number) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.state = state;
    }


}
