
public class Book {
    private String name;
    private String author;
    private Integer time;
    private String publisher;
    private int pages;
    private int count;

    public Book (){}

    public Book (String name, String author, Integer time, String publisher, Integer pages, Integer count){
        this.name = name;
        this.author = author;
        this.time = time;
        this.publisher = publisher;
        this.pages = pages;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "\"" + name + '\"' +
                " written by " + author +
                " in " + time +
                " was published " + publisher +
                ". It has " + pages +
                " pages and it's quantity " + count +
                '.';
    }
}
