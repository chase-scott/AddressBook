package com.example.lab4.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    private List<BuddyInfo> buddyBook;

    public AddressBook() {
        this.buddyBook = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BuddyInfo> getBuddyBook() {
        return this.buddyBook;
    }

    public void addBuddy(BuddyInfo newBuddy) {
        if (!buddyBook.contains(newBuddy)) buddyBook.add(newBuddy);
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddyBook.remove(buddy);
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                ", buddyBook=" + buddyBook +
                '}';
    }

}
