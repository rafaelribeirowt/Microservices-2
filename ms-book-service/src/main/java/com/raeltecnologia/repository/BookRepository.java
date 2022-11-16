package com.raeltecnologia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raeltecnologia.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
