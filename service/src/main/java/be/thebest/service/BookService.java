package be.thebest.service;

import be.thebest.domain.exception.IllegalFieldException;
import be.thebest.domain.exception.NotFoundException;
import be.thebest.domain.objects.Book;
import be.thebest.domain.repositories.BookRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@Named
public class BookService {
    private BookRepository bookRepository;

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookRepository.getAllBooks().values());
    }

    public Book getBookByIsbn(String isbn) {
        assertIsbnIsPresent(isbn);
        return bookRepository.getBookByIsbn(isbn);
    }

    public Book registerNewBook(Book book){
        assertIsbnIsNotUsed(book.getIsbn());
        bookRepository.registerNewBook(book);
        return book;
    }

    private void assertIsbnIsPresent(String isbn){
        if (bookRepository.getAllBooks().get(isbn) == null){
            throw new NotFoundException("This ISBN does not match any book");
        }
    }

    private void assertIsbnIsNotUsed(String isbn){
        if (bookRepository.getAllBooks().get(isbn) != null){
            throw new IllegalFieldException("This ISBN is already used");
        }
    }

}
