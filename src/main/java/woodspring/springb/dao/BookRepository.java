package woodspring.springb.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import woodspring.springb.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	   List<Book> findByTitle(String title);
	   List<Book> findByAuthor(String author);
	   Optional<Book> findBookByAuthor(String author);
	// custom query example and return a stream
	   @Query("select b from Book b where b.author = :author ")
	    Stream<Book> findByAuthorReturnStream(@Param("author") String author);


}
