package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.WriterService;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryController {

    private final WriterService writerService;
    private final BookService bookService;
    private final ReaderService readerService;

    public LibraryController(WriterService writerService, BookService bookService, ReaderService readerService) {
        this.writerService = writerService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/findBooksByReader/{id}")
    public List<Book> findBooksByReader(@PathVariable Long id) {
        return readerService.getBookByReaderId(id);
    }

    @GetMapping("/findBooksByAuthor/{id}")
    public List<Book> findBooksByAuthor(@PathVariable Long id) {
        return writerService.getBookByAuthorId(id);
    }

    @GetMapping("/getReaderByBook/{id}")
    public Reader getReaderByBookId(@PathVariable Long id) {
        return bookService.getReaderByBookId(id);
    }

    @GetMapping("/getReadersByAuthor/{id}")
    public List<Reader> getReadersByAuthorId(@PathVariable Long id) {
        return writerService.getReaderByAuthorId(id);
    }

}
