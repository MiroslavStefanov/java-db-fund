package com.spring_data.intro.homework;

import com.spring_data.intro.homework.enumerations.AgeRestriction;
import com.spring_data.intro.homework.enumerations.EditionType;
import com.spring_data.intro.homework.model.entities.Author;
import com.spring_data.intro.homework.model.entities.Book;
import com.spring_data.intro.homework.model.entities.Category;
import com.spring_data.intro.homework.services.AuthorService;
import com.spring_data.intro.homework.services.BookService;
import com.spring_data.intro.homework.services.CategoryService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Runner implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    private final Resource authorsRes;
    private final Resource categoriesRes;
    private final Resource booksRes;

    @Autowired
    public Runner(AuthorService authorService,
                  CategoryService categoryService,
                  BookService bookService,
                  @Value(value = "classpath:authors.txt")
                          Resource authorsRes,
                  @Value(value = "classpath:categories.txt")
                          Resource categoriesRes,
                  @Value(value = "classpath:books.txt")
                          Resource booksRes) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.authorsRes = authorsRes;
        this.categoriesRes = categoriesRes;
        this.booksRes = booksRes;
    }

    @Override
    public void run(String... args) throws Exception {

        initAuthors();
        initCategories();
        initBooks();

        //printTitlesAfter2000();
        //printAuthorsWithBooksBefore1990();
        //printAuthorsBookCount();
        //printAllBooksByGeorgePowell();
    }

    private void initAuthors() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(authorsRes.getFile()));

        List<Author> authors = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            String[] args = line.split("\\s+");
            authors.add(new Author(args[0], args[1]));
            line = reader.readLine();
        }

        this.authorService.saveIntoDB(authors);
    }

    private void initCategories() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(categoriesRes.getFile()));

        List<Category> categories = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            if (line.length() > 0) {
                categories.add(new Category(line));
            }
            line = reader.readLine();
        }

        this.categoryService.saveIntoDB(categories);
    }

    private void initBooks() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader(booksRes.getFile()));

        List<Book> books = new ArrayList<>();
        List<Author> authors = this.authorService.getAll();

        Random random = new Random();
        String line = reader.readLine();
        while (line != null) {
            String[] data = line.split("\\s+");

            int authorId = random.nextInt(authors.size());
            Author author = authors.get(authorId);
            int editionType = Integer.parseInt(data[0]);
            SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
            Date releaseDate = df.parse(data[1]);
            int copies = Integer.parseInt(data[2]);
            BigDecimal price = new BigDecimal(data[3]);
            int ageRestriction = Integer.parseInt(data[4]);
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }
            String title = titleBuilder.substring(0, titleBuilder.length() - 1);

            Book b = new Book();
            b.setAuthor(author);
            b.setEditionType(editionType);
            b.setReleaseDate(releaseDate);
            b.setCopies(copies);
            b.setPrice(price);
            b.setAgeRestriction(ageRestriction);
            b.setTitle(title);
            books.add(b);

            line = reader.readLine();
        }

        this.bookService.saveIntoDB(books);
    }

    private void printTitlesAfter2000() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        List<String> titles = this.bookService.getAllTitlesAfter(df.parse("2000"));
        System.out.println(String.join("\r\n", titles));
    }

    private void printAuthorsWithBooksBefore1990() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        List<String> authors = this.authorService.getAllWithBooksBefore(df.parse("1990"));
        System.out.println(String.join("\r\n", authors));
    }

    private void printAuthorsBookCount() {
        List<Author> authors = this.authorService.getAll();
        authors.stream()
                .sorted((x, y) -> {
                    Hibernate.initialize(x.getBooks());
                    Hibernate.initialize(y.getBooks());
                    return Integer.compare(y.getBooks().size(), x.getBooks().size());
                })
                .forEach(x -> {
                    System.out.printf("%s %s - %d books\r\n", x.getFirstName(), x.getLastName(), x.getBooks().size());
                });
    }

    private void printAllBooksByGeorgePowell() {
        List<Book> books = this.bookService.getAllBooksByAuthorName("George", "Powell");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        books.stream()
                .sorted((x, y) -> {
                    int dateComp = y.getReleaseDate().compareTo(x.getReleaseDate());
                    if(dateComp != 0)
                        return dateComp;
                    else
                        return x.getTitle().compareTo(y.getTitle());
                })
                .forEach(x -> System.out.printf("%s(%s) - %d copies\r\n",
                        x.getTitle(),
                        df.format(x.getReleaseDate()),
                        x.getCopies()));
    }

}
