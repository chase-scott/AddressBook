package com.example.lab4;

import com.example.lab4.model.AddressBook;
import com.example.lab4.model.BuddyInfo;
import com.example.lab4.repository.AddressBookRepository;
import com.example.lab4.repository.BuddyInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab6Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab6Application.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyInfoRepository) {
        return (args) -> {

            AddressBook addressBook = new AddressBook();
            addressBookRepository.save(addressBook);

            // save a few buddy info objects
            for (BuddyInfo b : buddies) {
                b.setAddressBook(addressBook);
                addressBook.addBuddy(b);
                buddyInfoRepository.save(b);
            }

        };
    }

    private static final BuddyInfo[] buddies = new BuddyInfo[] {
            new BuddyInfo("The Doctor", "The TARDIS", "07700 900461")
    };

}
