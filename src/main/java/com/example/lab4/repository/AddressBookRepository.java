package com.example.lab4.repository;

import com.example.lab4.model.AddressBook;
import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
}
