package com.igor.bookservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igor.bookservice.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
