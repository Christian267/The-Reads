package io.christian.thereads.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookControlller {
    
    GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId) {
        
    }
}
