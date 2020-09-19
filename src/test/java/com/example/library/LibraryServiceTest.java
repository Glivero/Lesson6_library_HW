package com.example.library;

import com.example.library.model.Writer;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.WriterService;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class LibraryServiceTest {

    @Autowired
    private WriterService writerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;
    private List<Writer> writerList;
    private List<Reader> readerList;
    private List<Book> bookList;

    @Test
    public void BookByReaderIdTest() {
        Long id = readerList.get(2).getId();
        List<Book> books = readerService.getBookByReaderId(id);
        Assertions.assertThat(books).hasSize(4);
        Assertions.assertThat(books.stream().map(Book::getName).collect(Collectors.toList())).contains("Евгений Онегин", "Война и мир", "Кавказский пленник", "Мертвые души");
    }

    @Test
    public void BookByWriterIdTest() {
        Long id = writerList.get(1).getId();
        List<Book> books = writerService.getBookByAuthorId(id);
        Assertions.assertThat(books).hasSize(3);
    }

    @Test
    public void ReaderByBookIdTest() {
        Long id = bookList.get(3).getId();
        Reader reader = bookService.getReaderByBookId(id);
        Assertions.assertThat(reader.getName()).isEqualTo("Анна");
    }

    @Test
    public void ReaderByWriterIdTest() {
        Long id = writerList.get(2).getId();
        List<Reader> readerList = writerService.getReaderByAuthorId(id);
        Assertions.assertThat(readerList).hasSize(2);
    }

    @BeforeAll
    public void testData() {
        readerList = new ArrayList<>();
        readerList.add(new Reader("Денис"));
        readerList.add(new Reader("Анна"));
        readerList.add(new Reader("Даша"));
        readerList = readerList
                .stream()
                .peek(readerService::save)
                .collect(Collectors.toList());

        writerList = new ArrayList<>();
        writerList.add(new Writer("А.С.Пушкин"));
        writerList.add(new Writer("Л.Н.Толстой"));
        writerList.add(new Writer("Н.В.Гоголь"));
        writerList = writerList
                .stream()
                .peek(writerService::save)
                .collect(Collectors.toList());

        bookList = new ArrayList<>();
        bookList.add(new Book("Евгений Онегин", readerList.get(2), writerList.get(0)));
        bookList.add(new Book("Капитанская дочка", readerList.get(0), writerList.get(0)));
        bookList.add(new Book("Война и мир", readerList.get(2), writerList.get(1)));
        bookList.add(new Book("Анна Каренина", readerList.get(1), writerList.get(1)));
        bookList.add(new Book("Кавказский пленник", readerList.get(2), writerList.get(1)));
        bookList.add(new Book("Вий", readerList.get(0), writerList.get(2)));
        bookList.add(new Book("Мертвые души", readerList.get(2), writerList.get(2)));
        bookList = bookList
                .stream()
                .peek(bookService::save)
                .collect(Collectors.toList());
    }
}
