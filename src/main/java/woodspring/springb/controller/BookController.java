package woodspring.springb.controller;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import woodspring.springb.service.BookService;
import woodspring.springb.book.exception.BookNotFoundException;
import woodspring.springb.dao.BookRepository;
import woodspring.springb.entity.Book;

@RestController
@RequestMapping("/book")
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class BookController {
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired 
	BookService bookService;
	
	GsonBuilder gBuilder = new GsonBuilder();
	
	//@Autowired 
	//BookRepository bookRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	List<Book> listBooks() {
		return bookService.listBooks();
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
	//ResponseEntity<?> readBook(@PathVariable int bookId) {
	Book readBook(@PathVariable int bookId) {
		logger.info(" /SpringB/book/"+ bookId);
		gBuilder = new GsonBuilder();
		gBuilder.excludeFieldsWithModifiers(Modifier.PRIVATE);
		
		Book aBook = bookService.findBookById( bookId );
		logger.info("-0- /SpringB/book/"+ bookId+" aBook:["+ aBook+"]");
		//Gson gson = gBuilder.create();
		Gson gson = new Gson();
		logger.info("-1-- /SpringB/book/"+ bookId+" aBook:["+ aBook+"]");
		//String bookStr = gson.toJson(aBook);
		//ogger.info("-2- /SpringB/book/"+ bookId+" aBook:["+ aBook+"] theBook:["+bookStr+"]");
		return aBook; //ResponseEntity.ok( aBook); //bookStr);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/author/{author}")
	List<Book> getBookByAuthor(@PathVariable String author) {
		//validateAuthor( author);
		logger.info(" /SpringB/author/"+ author);
		return bookService.findBookByAuthor( author);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String author, @RequestBody Book aBook) {
		//this.validateAuthor(author);
		Book theBook =  bookService.newBook(author, aBook);
		return ResponseEntity.ok(theBook);
/*		
//		return bookService.newBook( author, aBook)
//				.map(account -> {
//					Bookmark result = bookmarkRepository.save(new Bookmark(account,
//							input.uri, input.description));
//
//					URI location = ServletUriComponentsBuilder
//						.fromCurrentRequest().path("/{id}")
//						.buildAndExpand(result.getId()).toUri();
//
//					return ResponseEntity.created(location).build();
//				})
//				.orElse(ResponseEntity.noContent().build());
*/
	}


//	private <X> void validateAuthor(String author) {
//		bookRepository.findBookByAuthor(author)
//			.orElseThrow((Supplier<? extends Exception>)() -> new BookNotFoundException(author));
//	}

}
