package com.bsa.boot.controller;

import com.bsa.boot.exception.EntityNotFoundException;
import com.bsa.boot.exception.ValidationException;
import com.bsa.boot.service.GameOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class WebGameController {
    private final GameOperationService gameOperationService;

    @Autowired
    public WebGameController(GameOperationService gameOperationService) {
        this.gameOperationService = gameOperationService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/games")
    public String renderGamesList(Model model) {
        model.addAttribute("games", gameOperationService.getAll());

        return "games.list";
    }

    @GetMapping("/games/{id}")
    public ModelAndView renderGames(@PathVariable String id, ModelAndView modelAndView) {
        try {
            modelAndView.setViewName("game.item");
            modelAndView.addObject("game", gameOperationService.findById(id));
        } catch (EntityNotFoundException | ValidationException ex) {
            modelAndView.setViewName("error.404");
        }

        return modelAndView;
    }
}