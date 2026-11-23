package edu.handong.week12.book.service;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import edu.handong.week12.book.exception.BookNotFoundException;
import edu.handong.week12.book.repository.BookRepository;
import edu.handong.week12.book.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookResponse create(BookRequest bookRequest) {

        Book newBook = new Book(bookRequest.title(), bookRequest.title(), bookRequest.price());

        return BookResponse.from(repository.save(newBook));
    }

    public List<BookResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(BookResponse::from).toList();
    } // TODO STEP 6: Pagination/Sorting

    public BookResponse findById(Long id) {
        return repository.findById(id).map(BookResponse::from).orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookResponse update(Long id, Book input) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.update(input.getTitle(), input.getAuthor(), input.getPrice());

        return BookResponse.from(repository.save(book));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new BookNotFoundException(id);

        repository.deleteById(id);
    }
}
