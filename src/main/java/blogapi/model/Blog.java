package blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Blog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String html;
    @JsonIgnore
    private String str_date;
    private LocalDate date;

    public Blog()
    {
    }

    public Blog(long id, String title, String html, LocalDate date)
    {
        this.id = id;
        this.title = title;
        this.html = html;
        this.date = date;
        this.str_date = date.toString();
    }

    // Getters and setters
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    @JsonIgnore
    public String getStr_Date()
    {
        return str_date;
    }

    public void setStr_date(String date)
    {
        this.str_date = date;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public static String convertDateToString(LocalDate lDate, String customFormat)
    {
        if (customFormat == null)
        {
            customFormat = "MM/dd/yy";
        }
        else if (customFormat.isEmpty())
        {
            customFormat = "MM/dd/yy";
        }
        else if (customFormat.isBlank())
        {
            customFormat = "MM/dd/yy";
        }

        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(customFormat);
        String tDate = lDate.format(dtFormatter);
        return tDate.toString();
    }
}
