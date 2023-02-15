package com.example.lab4;

import com.example.lab4.model.AddressBook;
import com.example.lab4.model.BuddyInfo;
import com.example.lab4.repository.AddressBookRepository;
import com.example.lab4.repository.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;
    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    //Gets all address books
    @GetMapping("/")
    public String getAddressBooks(Model model) {
        Iterable<AddressBook> books = addressBookRepository.findAll();
        model.addAttribute("addressBooks", books);
        return "index";
    }

    @GetMapping("/addressbooks/{addressBookId}")
    public String getAddressBook(@PathVariable Long addressBookId, Model model) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + addressBookId));
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddyInfo", new BuddyInfo());
        return "addressbook";
    }

    @PostMapping("/")
    public String createAddressBook(@ModelAttribute AddressBook addressBook) {
        addressBookRepository.save(addressBook);
        return "redirect:/";
    }

    @PostMapping("/addressbooks/{addressBookId}")
    public String addBuddy(@PathVariable Long addressBookId, @ModelAttribute BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + addressBookId));

        buddyInfo.setAddressBook(addressBook);
        buddyInfoRepository.save(buddyInfo);

        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);

        return "redirect:/addressbooks/" + addressBookId;
    }

    @PostMapping("/addressbooks/{addressBookId}/{buddyId}")
    public String removeBuddy(@PathVariable Long addressBookId, @PathVariable Long buddyId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + addressBookId));
        BuddyInfo buddyInfo = buddyInfoRepository.findById(buddyId).orElseThrow(() -> new RuntimeException("Error finding buddy with id:" + buddyId));
        addressBook.removeBuddy(buddyInfo);
        buddyInfoRepository.delete(buddyInfo);
        addressBookRepository.save(addressBook);

        return "redirect:/addressbooks/" + addressBookId;
    }

}
