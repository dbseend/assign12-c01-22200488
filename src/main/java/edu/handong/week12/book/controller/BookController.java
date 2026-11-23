package edu.handong.week12.book.controller;

import edu.handong.week12.book.domain.Book;
import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import edu.handong.week12.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book", description = "도서 관리 API")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }


    @Operation(summary = "도서 추가")
    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest bookRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(bookRequest));
    }

    @Operation(summary = "도서 목록 조회")
    @GetMapping
    public ResponseEntity<List<BookResponse>> list(@PageableDefault(size = 5, sort = "price", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "도서 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "도서 수정")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @RequestBody Book input) {
        return ResponseEntity.ok(service.update(id, input));
    }

    @Operation(summary = "도서 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
