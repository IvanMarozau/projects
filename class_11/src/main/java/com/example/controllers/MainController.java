package com.example.controllers;

import com.example.models.Review;
import com.example.models.Role;
import com.example.models.User;
import com.example.repo.ReviewRepository;
import com.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired//мы ссылаемся на класс review
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/") //отслеживаем переход на главную страницу
   // public String home(@RequestParam(name="userName", required = false, defaultValue = "World") String userName,Model model){//@RequestParam говорит то что мы будем получать информацию из url адреса
        public String home(Model model){
        model.addAttribute("name", "World");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "Страница про нас");
        return "about";
    }
    @GetMapping("/reviews")
    public String revivews(Model model){
        Iterable<Review> reviews=reviewRepository.findAll();//список массив
        model.addAttribute("title", "Страница с отзывами");
        model.addAttribute("reviews", reviews);
        return "reviews";
    }
    @PostMapping("/reviews-add")
    public String reviewsAdd(@AuthenticationPrincipal User user, @RequestParam String title, @RequestParam String text, Model model) {//@RequestParam позволяет брать значения из формы
        Review review = new Review(title, text, user);
        reviewRepository.save(review);

        return "redirect:/reviews";
    }
    @GetMapping("/reviews/{id}")
    public String reviewInfo(@PathVariable(value = "id") long id, Model model){//@PathVariable отслеживает переменную {id}
        Optional<Review> review=reviewRepository.findById(id);//одна запись

        ArrayList<Review> result=new ArrayList<>();
        review.ifPresent(result::add);//помещаем все записи reviews в arraylist

        model.addAttribute("review",result);
        return "review-info";
    }
    @GetMapping("/reviews/{id}/update")
    public String reviewUpdate(@PathVariable(value = "id") long id, Model model){
        Optional<Review> review=reviewRepository.findById(id);//одна запись

        ArrayList<Review> result=new ArrayList<>();
        review.ifPresent(result::add);//помещаем все записи reviews в arraylist

        model.addAttribute("review",result);
        return "review-update";
    }
    @PostMapping("/reviews/{id}/update")
    public String reviewsUpdateForm(@PathVariable(value = "id") long id,@RequestParam String title, @RequestParam String text, Model model) throws ClassNotFoundException {//@RequestParam позволяет брать значения из формы
        Review review=reviewRepository.findById(id).orElseThrow(()->new ClassNotFoundException());//orElseThrow проверям на ошибку если запись не будет найдена
        review.setTitle(title);
        review.setText(text);
        reviewRepository.save(review);
        return "redirect:/reviews/"+id;
    }
    @PostMapping("/reviews/{id}/delete")
    public String reviewsDelete(@PathVariable(value = "id") long id,Model model) throws ClassNotFoundException {
        Review review=reviewRepository.findById(id).orElseThrow(()->new ClassNotFoundException());

        reviewRepository.delete(review);
        return "redirect:/reviews";
    }
    @GetMapping("/reg")
    public String reg(){
        return "reg";
    }
    @PostMapping("/reg")
    public String addUser(User user, Model model){
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));//устанавливаем роль юзеру
        userRepository.save(user);
        return "redirect:/login";
    }
}

