package org.generation.italy.controller;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllSortByName());
		return "/pizzas/list";
	}

	// GET (x create)
	@GetMapping("/edit")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza()); // crea oggetto Pizza vuoto da riempire tramite form
		model.addAttribute("ingredientList", ingredientService.findAllSortByIngredient());
		return "/pizzas/edit";
	}
	
	// POST (x create)
	@PostMapping("/edit")
	public String doCreate(@ModelAttribute("pizza") Pizza formPizza, Model model) {
		// TODO validation
		service.save(formPizza);
		return "redirect:/pizzas"; // quando compilato form rimanda ad elenco aggiornato
	}
	
}
