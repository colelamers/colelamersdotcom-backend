package blogapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class QuoteInfo
{
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String quote;
    private String author;
    public QuoteInfo() { }

    public QuoteInfo(String quote, String author)
    {
        this.quote = quote;
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }

    public String getQuote(){
        return this.quote;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setQuote(String quote){
        this.quote = quote;
    }
}
