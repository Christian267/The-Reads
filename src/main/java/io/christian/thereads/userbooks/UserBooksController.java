package io.christian.thereads.userbooks;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserBooksController {
    
    @PostMapping("/addUserBook")
    public String addBookForUser(
        @RequestBody MultiValueMap<String, String> formData,
        @AuthenticationPrincipal OAuth2User principal)  {
        System.out.println("THIS IS THE FORM DATA");
        System.out.println(formData);

        return "five";
    }
}
