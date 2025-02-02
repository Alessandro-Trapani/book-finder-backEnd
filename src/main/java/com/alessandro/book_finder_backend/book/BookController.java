package com.alessandro.book_finder_backend.book;

import com.alessandro.book_finder_backend.book.BookService;
import com.alessandro.book_finder_backend.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDto book) {

        try{
            bookService.addBookToUser(book.getEmail(), book.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("confirmation", "added correctly"));
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "User not found"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e));
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeBook(@RequestBody BookDto book) {
        try{
            bookService.removeBookFromUser(book.getEmail(), book.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("confirmation", "book deleted from favorites"));
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "User not found"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e));
        }



    }


    @GetMapping("/getBooks")
    public ResponseEntity<?> getBooks(@RequestBody String email){
        try{
            Set<String> bookIds = bookService.getBooks(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("books", bookIds));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e));
        }

    }
}
