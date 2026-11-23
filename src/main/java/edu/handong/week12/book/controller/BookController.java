package edu.handong.week12.book.controller;

import edu.handong.week12.book.domain.Book;
import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import edu.handong.week12.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    // Swagger(OpenAPI) 문서에서 해당 API 엔드포인트의 요약 설명을 "도서 추가"로 지정
    @Operation(summary = "도서 추가")
    @PostMapping
    // 요청 본문(@RequestBody)의 JSON 데이터를 BookRequest DTO로 역직렬화하고, @Valid로 유효성 검사를 수행
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest bookRequest) {

        // HTTP 응답 상태: 201(Created)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                // 서비스 계층에 도서 생성 비즈니스 로직을 전달한 뒤 반환된 BookResponse DTO를 응답 본문(Body)에 담아 반환
                .body(service.create(bookRequest));
    }

    @Operation(summary = "도서 목록 조회")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(@PageableDefault(size = 5, sort = "price", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "도서 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "도서 수정")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest input) {
        return ResponseEntity.ok(service.update(id, input));
    }

    @Operation(summary = "도서 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
