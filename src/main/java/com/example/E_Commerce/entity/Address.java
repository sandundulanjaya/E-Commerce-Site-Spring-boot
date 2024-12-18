package com.example.E_Commerce.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    public static Address createEmpty() {
        return Address.builder()
                .street("")
                .city("")
                .state("")
                .country("")
                .postalCode("")
                .build();
    }
}


