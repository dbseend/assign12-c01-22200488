package edu.handong.week12.book.service;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import edu.handong.week12.book.exception.BookNotFoundException;
import edu.handong.week12.book.repository.BookRepository;
import edu.handong.week12.book.domain.Book;
import org.springframework.data.domain.Page;
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

        Book newBook = new Book(bookRequest.title(), bookRequest.author(), bookRequest.price());

        return BookResponse.from(repository.save(newBook));
    }

    // 페이징 및 정렬 조건(Pageable)을 받아 조건에 맞는 도서 목록을 페이지 단위로 조회하는 메소드
    public Page<BookResponse> findAll(Pageable pageable) {
        // repository.findAll(pageable)로 조회한 Page<Book> 객체 내부의 각 Book 엔티티를 BookResponse DTO로 변환하여 Page<BookResponse>로 반환
        return repository.findAll(pageable).map(BookResponse::from);
    }

    // id 로 단건 도서를 조회하여 응답 DTO(BookResponse)로 반환하는 메소드
    public BookResponse findById(Long id) {
        // repository.findById(id): DB에서 해당 ID의 Book 엔티티를 Optional<Book> 형태로 조회
        // .map(BookResponse::from): 조회된 엔티티가 존재하면 from 함수를를 통해 BookResponse DTO로 변환
        return repository.findById(id)
                .map(BookResponse::from)
                // .orElseThrow(...): 조회된 데이터가 없으면 BookNotFoundException 예외를 발생
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookResponse update(Long id, BookRequest input) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.update(input.title(), input.author(), input.price());

        return BookResponse.from(repository.save(book));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new BookNotFoundException(id);

        repository.deleteById(id);
    }
}
