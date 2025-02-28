// BlogController.java
package blogapi.controller;

import blogapi.model.Blog;
import blogapi.service.PostgreSQLQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/blogs") // NOTE: caps does matter on server!
@CrossOrigin(origins = "http://www.colelamers.com") // Enable CORS for this controller
//@CrossOrigin(origins = "http://localhost:4000") // Enable CORS for this controller
public class BlogController
{
    private final PostgreSQLQueryWrapper psqlWrapper;
    public BlogController(PostgreSQLQueryWrapper psqlWrapper)
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

    // Endpoint to get a list of all blogs
    // @GetMapping("/")
    @GetMapping
    public List<Blog> getAllBlogs() throws Exception
    {
        String sql = "SELECT id, title, html, date FROM blogs ORDER BY date desc";
        List<Blog> blogs = new ArrayList<>();
        var results = psqlWrapper.executeQueryAs(sql, new Object[]{});
        for (Object[] row : results)
        {
            String[] ytdSplit = row[3].toString().split("-");
            LocalDate ld = LocalDate.of(Integer.parseInt(ytdSplit[0]), Integer.parseInt(ytdSplit[1]),
                                        Integer.parseInt(ytdSplit[2]));

            Blog blog = new Blog(Long.parseLong(row[0].toString()),
                                 row[1].toString(), row[2].toString(), ld);
            blogs.add(blog);
        }
        return blogs;
    }

    @GetMapping("/titles")
    public List<Blog> getTitles() throws Exception
    {
        String sql = "SELECT id, title FROM blogs ORDER BY date desc";
        List<Blog> blogs = new ArrayList<>();
        var results = psqlWrapper.executeQueryAs(sql, new Object[]{});
        for (Object[] row : results)
        {
            Blog blog = new Blog();
            blog.setId(Long.parseLong(row[0].toString()));
            blog.setTitle(row[1].toString());
            blogs.add(blog);
        }
        return blogs;
    }

    // Endpoint to get a specific blog by ID
    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable("id") long id) throws Exception
    {
        List<Blog> blogs = new ArrayList<>();
        try{
            String sql = "SELECT id, title, html, CAST(date as text) FROM blogs WHERE id = ?";
            blogs = psqlWrapper.executeQueryAsClassList(sql, Blog.class, new Object[]{ id });
        }
        catch(Exception ex){
            // todo 2; fill with logging somewhere?
        }
        // todd 1; need to add a way to return an error so that when data is empty
        // (because of bad id passed in) i can handle that and send the user to my error page.
        return blogs.isEmpty() ? null : blogs.get(0);
    }
}

    /*// Endpoint to get a specific blog by ID
    @GetMapping("/api/blogs/{id}")
    public Blog getBlogById(@PathVariable("id") long id) throws IOException
    {
        ArrayList<Blog> blogs = new ArrayList<>(getBlogsById(id));
        try{
            if (blogs != null)
            {
                Blog result = null;
                for (int i = 0; i < blogs.size(); ++i)
                {
                    Blog x = blogs.get(i);
                    if (x.getId() == id) {
                        result = x;
                        break;
                    }
                }
                return result;
            }
            else
            {
                return null;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
    }*/
