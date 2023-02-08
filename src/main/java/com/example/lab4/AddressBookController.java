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
    @ResponseBody
    public Iterable<AddressBook> getAddressBooks() {
        return addressBookRepository.findAll();
    }

    @GetMapping("/addressbooks/{id}")
    public String getAddressBook(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + id));
        model.addAttribute("addressBookId", id);
        model.addAttribute("buddies", addressBook.getBuddyBook());
        return "addressbook";
    }

    @PostMapping("/")
    @ResponseBody
    public AddressBook createAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    @PutMapping("/{addressBookId}/buddies")
    @ResponseBody
    public AddressBook addBuddy(@PathVariable Long addressBookId, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + addressBookId));
        buddyInfo.setAddressBook(addressBook);
        buddyInfoRepository.save(buddyInfo);
        addressBook.addBuddy(buddyInfo);
        return addressBookRepository.save(addressBook);
    }

    @DeleteMapping("/{addressBookId}/buddies/{buddyId}")
    @ResponseBody
    public AddressBook removeBuddy(@PathVariable Long addressBookId, @PathVariable Long buddyId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId).orElseThrow(() -> new RuntimeException("Error finding addressBook with id:" + addressBookId));
        BuddyInfo buddyInfo = buddyInfoRepository.findById(buddyId).orElseThrow(() -> new RuntimeException("Error finding buddy with id:" + buddyId));
        addressBook.removeBuddy(buddyInfo);
        buddyInfoRepository.delete(buddyInfo);
        return addressBookRepository.save(addressBook);
    }

}
