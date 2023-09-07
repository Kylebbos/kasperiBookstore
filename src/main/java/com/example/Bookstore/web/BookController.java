package com.example.Bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import com.example.Bookstore.domain.Book;

@Controller
public class BookController {

    @GetMapping("/index")
    public String index(Model model) {
 
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", "Matti Pena", 2023, "ISBN-1", 29.99));
        books.add(new Book("Book 2", "Kassu Kessu", 2022, "ISBN-2", 19.99));

        
        model.addAttribute("books", books);

        return "index";
    }
}

