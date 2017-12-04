package woodspring.springb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import woodspring.springb.dao.BookRepository;
import woodspring.springb.dao.PublisherRepository;
import woodspring.springb.entity.Book;

@Service
public class BookService {
	 private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	 
	 @Autowired
	 BookRepository bookRepository;
	 
	 @Autowired
	 PublisherRepository publisherRepository;
	 
	 public List<Book> listBooks() {
		 return bookRepository.findAll();
	 }
	 
	 public Book findBookById(int book_id) {
		 Book aBook =  bookRepository.findOne( book_id); //.getOne( book_id); //Integer.valueOf((book_id)));
		 logger.info("bookRepository.getOne("+book_id+"), aBook:["+aBook+"]");
		 return aBook;
	 }

	 public List<Book> findBookByTitle(String title) {
		 return bookRepository.findByTitle( title);
	 }
	 public List<Book> findBookByAuthor(String author) {
		 return bookRepository.findByAuthor( author);
	 }
	 
	 public Book newBook( String author, Book aBook) {
		 bookRepository.findByAuthorReturnStream( author);
		Book theBook = bookRepository.saveAndFlush(aBook);	
		return theBook;
				 
				 
//					return bookService.newBook( author, aBook)
//							.map(account -> {
//								Bookmark result = bookmarkRepository.save(new Bookmark(account,
//										input.uri, input.description));
//
//								URI location = ServletUriComponentsBuilder
//									.fromCurrentRequest().path("/{id}")
//									.buildAndExpand(result.getId()).toUri();
//
//								return ResponseEntity.created(location).build();
//							})
//							.orElse(ResponseEntity.noContent().build());

	 }
}
