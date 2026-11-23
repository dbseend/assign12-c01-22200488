package edu.handong.week12.book.service;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import edu.handong.week12.book.repository.BookRepository;
import edu.handong.week12.book.domain.Book;
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

  public List<BookResponse> findAll() {
    return repository.findAll().stream().map(BookResponse::from).toList();
  } // TODO STEP 6: Pagination/Sorting

  public BookResponse findById(Long id) {
    return repository.findById(id).map(BookResponse::from).orElseThrow();
  } // TODO STEP 5: 404

  public BookResponse update(Long id, Book input) {
    Book book = repository.findById(id).orElseThrow();
    book.update(input.getTitle(), input.getAuthor(), input.getPrice());
    return BookResponse.from(repository.save(book));
  }

  public void delete(Long id) {
    repository.deleteById(id);
  } // TODO: 404 if absent
}
