package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Reader getReaderByBookId(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(Book::getReader).orElse(null);
    }
}
