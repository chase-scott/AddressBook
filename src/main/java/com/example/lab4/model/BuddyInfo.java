package com.example.lab4.model;

import com.example.lab4.model.AddressBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    @ManyToOne
    @JsonIgnore
    private AddressBook addressBook;

    public BuddyInfo() {
    }

    public BuddyInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressBook getAddressBook() {return addressBook;}

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "BuddyInfo[id=%d, name='%s', address='%s', phoneNumber=%s]",
                id, name, address, phoneNumber);
    }

}
