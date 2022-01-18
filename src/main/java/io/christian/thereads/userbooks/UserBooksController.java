package io.christian.thereads.userbooks;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * API responsible for writing into book_by_user_and_bookid table
 */
@Controller
public class UserBooksController {
    
    @Autowired
    UserBooksRepository userBooksRepository;

    public UserBooksController(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }


    /** 
     * Writes the form input from "/books/{bookId}" into Cassandra. 
     * @param formData The input form data from book.html contains 6 keys
     * {"startDate", "completedDate", "rating", "status", "bookId", "_csrf"}
     * @param principal OAuth2User instance, for finding userId
     * @return redirects to original page, effectively refreshing "/book/{bookId}"
    */
    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(
        @RequestBody MultiValueMap<String, String> formData,
        @AuthenticationPrincipal OAuth2User principal)  
        {
        if (principal == null || principal.getAttribute("login") == null) {
            return null;
        }

        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        String bookId = formData.getFirst("bookId");
        key.setUserId(principal.getAttribute("login"));
        key.setBookId(bookId);
        
        userBooks.setKey(key);
        userBooks.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userBooks.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userBooks.setRating(Integer.parseInt(formData.getFirst("rating")));
        userBooks.setReadingStatus(formData.getFirst("status"));

        userBooksRepository.save(userBooks);

        return new ModelAndView("redirect:/books/" + bookId);
    }
}
