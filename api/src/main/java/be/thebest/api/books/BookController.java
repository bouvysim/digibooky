package be.thebest.api.books;

import be.thebest.domain.objects.Book;
import be.thebest.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "/books")
@RestController
public class BookController {
    private BookService bookService;

    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : bookService.getAllBooks()) {
            bookDtos.add(BookDtoMapper.bookMapper(book));
        }
        return bookDtos;
    }

    @GetMapping(path = "/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByIsbn(@PathVariable("isbn") String isbn) {
        List<BookDto> booksFound = new ArrayList<>();
        if (isbn.contains(".")) {
            for (Book book: bookService.getBookByIsbnWithWildCard(isbn)) {
                booksFound.add(BookDtoMapper.bookMapper(book));
            }
             return booksFound;
        }
        booksFound.add(BookDtoMapper.bookMapper(bookService.getBookByIsbn(isbn)));
        return booksFound;
    }

}
