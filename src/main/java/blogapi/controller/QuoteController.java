// BlogController.java
package blogapi.controller;

import blogapi.model.QuoteInfo;
import blogapi.service.PostgreSQLQueryWrapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "http://www.colelamers.com") // Enable CORS for this controller
// @CrossOrigin(origins = "http://localhost:4000") // Enable CORS for this controller
public class QuoteController
{
    private final PostgreSQLQueryWrapper psqlWrapper;
    public QuoteController(PostgreSQLQueryWrapper psqlWrapper)
    {
        this.psqlWrapper = psqlWrapper;
    }
    // Endpoint to get a list of all blogs
    @GetMapping("/index")
    public String index()
    {
        return "index";
    }

    // Endpoint to get a list of all blogs
    @GetMapping("/error")
    public String error()
    {
        return "error page";
    }

    // Endpoint to get a list of all quotes
    // @GetMapping("/")
    @GetMapping
    public List<QuoteInfo> getAllQuotes() throws Exception
    {
        var x = getQuotes();
        return x;
    }

    // Method to query all quotes
    private List<QuoteInfo> getQuotes()
    {
        String sql = "SELECT quote, author FROM quotes";
        return psqlWrapper.executeQueryAsClassList(sql, QuoteInfo.class, new Object[]{});
    }
}
