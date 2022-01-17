package io.christian.thereads.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.christian.thereads.userbooks.UserBooks;
import io.christian.thereads.userbooks.UserBooksPrimaryKey;
import io.christian.thereads.userbooks.UserBooksRepository;



/**
 * API responsible for returning html templates relating to books
 */
@Controller
public class BookControlller {
    
    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBooksRepository userBooksRepository;

    /**
     * 
     * @param bookId Book Id from the Cassandra database, normally obtained by clicking 
     * through a book card in the "search.html"
     * @param model Utilized to pass data to html templates using thymeleaf,
     * allowing for dynamic behavior
     * @param principal OAuth object for verifying userId
     * @return "book.html" if bookId is found in Cassandra, "book-not-found.html" otherwise
     */
    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            String coverImageUrl = "/images/noimage.png";
            if (book.getCoverIds() != null & book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-L.jpg";
                model.addAttribute("coverImage", coverImageUrl);
            }
            model.addAttribute("book", book);
            
            if(principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", principal.getAttribute("login"));
                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setBookId(bookId);
                key.setUserId(userId);
                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                if (userBooks.isPresent()) {
                    model.addAttribute("userBooks", userBooks.get());
                } else {
                    model.addAttribute("userBooks", new UserBooks());
                }

            }
            return "book";
        }
        return "book-not-found";
    }
}
