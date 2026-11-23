package edu.handong.week12.book.repository;
import edu.handong.week12.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long> {}
