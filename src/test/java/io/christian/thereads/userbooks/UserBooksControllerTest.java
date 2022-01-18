package io.christian.thereads.userbooks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@ExtendWith(MockitoExtension.class)
@DisplayName("userBooks controller test")
public class UserBooksControllerTest {

    @Mock
    private UserBooksRepository userBooksRepository;
    private UserBooksController userBooksController;

    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        userBooksController = new UserBooksController(userBooksRepository);
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
    }

    @Test
    void contextLoaded() {
        assertNotEquals(null, userBooksRepository);
    }

    @Test
    @Tag("HTTP-POST")
    @DisplayName("\"/addUserBook\", logged in")
    void testAddBookForUserLoggedIn() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("_csrf", "5cedf637-eac7-4c9f-9ccf-4d8fc9c4abe7");
        formData.add("bookId", "OL10002277W");
        formData.add("startDate", "2022-01-08");
        formData.add("completedDate", "2022-01-15");
        formData.add("readingStatus", "finished");
        formData.add("rating", "4");
        String nameAttributeKey = "nameAttributeKey";
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(nameAttributeKey, "hello");
        attributes.put("login", "yes");
        HashSet<OAuth2UserAuthority> authorities = new HashSet<OAuth2UserAuthority>();
        authorities.add(new OAuth2UserAuthority(attributes));
        DefaultOAuth2User principal = new DefaultOAuth2User(authorities, attributes, nameAttributeKey);

        
        assertEquals("redirect:/books/" + formData.getFirst("bookId"), userBooksController.addBookForUser(formData, principal).getViewName(), "should return \"redirect:/books/{bookId}\"");
    }

    @Test
    @Tag("HTTP-POST")
    @DisplayName("\"/addUserBook\", not logged in")
    void testAddBookForUserLoggedOut() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("_csrf", "5cedf637-eac7-4c9f-9ccf-4d8fc9c4abe7");
        formData.add("bookId", "OL10002277W");
        formData.add("startDate", "2022-01-08");
        formData.add("completedDate", "2022-01-15");
        formData.add("readingStatus", "finished");
        formData.add("rating", "4");
        String nameAttributeKey = "nameAttributeKey";
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(nameAttributeKey, "hello");
        HashSet<OAuth2UserAuthority> authorities = new HashSet<OAuth2UserAuthority>();
        authorities.add(new OAuth2UserAuthority(attributes));
        DefaultOAuth2User principal = new DefaultOAuth2User(authorities, attributes, nameAttributeKey);

        
        assertNull(userBooksController.addBookForUser(formData, principal), "should return null");
    }
}
