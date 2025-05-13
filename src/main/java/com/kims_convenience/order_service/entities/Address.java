package com.kims_convenience.order_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "default_address")
    private boolean defaultAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Address(String addressId, String name, String phoneNumber, String addressLine1, String addressLine2, String city, String state, String postalCode, String country, String addressType, boolean defaultAddress, Order order) {
        this.addressId = addressId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.addressType = addressType;
        this.defaultAddress = defaultAddress;
        this.order = order;
    }
}
