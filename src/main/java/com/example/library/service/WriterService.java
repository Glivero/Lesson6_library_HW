package com.example.library.service;

import com.example.library.model.Writer;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WriterService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;

    public WriterService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public List<Book> getBookByAuthorId(Long id) {
        Optional<Writer> writer = authorRepository.findById(id);
        if (writer.isPresent()) {
            return writer.get().getBooks();
        } else {
            return new ArrayList<>();
        }
    }

    public Writer save(Writer writer) {
        return authorRepository.save(writer);
    }

    public List<Reader> getReaderByAuthorId(Long id) {
        Optional<Writer> writer = authorRepository.findById(id);
        if (writer.isPresent()) {
            List<Book> books = writer.get().getBooks();
            if (books != null) {
                return books.stream()
                        .map(it -> bookService.getReaderByBookId(it.getId()))
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
