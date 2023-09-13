package com.example.Bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;

@Controller
public class BookController {

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@Autowired // Add this annotation
	private CategoryRepository categoryRepository;


	@GetMapping("/index")
	public String index(Model model) {

		List<Book> books = new ArrayList<>();
		books.add(new Book("Book 1", "Matti Pena", 2023, "ISBN-1", 29.99));
		books.add(new Book("Book 2", "Kassu Kessu", 2022, "ISBN-2", 19.99));

		model.addAttribute("books", books);

		return "index";
	}

	@GetMapping("/booklist")
	public String bookList(Model model) {
		Iterable<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "booklist";
	}

	@GetMapping("/addbook")
	public String showAddBookForm(Model model) {
	    Iterable<Category> categories = categoryRepository.findAll();
	    model.addAttribute("categories", categories);
	    return "addbook";
	}


    @PostMapping("/addbook")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
    	bookRepository.deleteById(id);
        return "redirect:/booklist";
    }
	
	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable Long id, Model model) {
	    
	    Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
	    model.addAttribute("book", book);
	    return "editbook";
	}

	@PostMapping("/edit/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute Book updatedBook) {
	    
	    Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
	    book.setTitle(updatedBook.getTitle());
	    book.setAuthor(updatedBook.getAuthor());
	    
	    bookRepository.save(book);
	    return "redirect:/booklist";
	}
}
