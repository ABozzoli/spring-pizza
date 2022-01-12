package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	// GET (create)
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false); // per far capire alla form se è un create o un update
		model.addAttribute("pizza", new Pizza()); // crea oggetto Pizza vuoto da riempire tramite form
		model.addAttribute("ingredientList", ingredientService.findAllSortByIngredient());
		return "/pizzas/edit";
	}
	
	// POST (create)
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza,
			BindingResult bindingResult, Model model, RedirectAttributes redAtt) {
		// se ci sono errori ritorna alla form
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredientList", ingredientService.findAllSortByIngredient());
			return "/pizzas/edit";
		}
		// se non ci sono errori salva i dati
		service.save(formPizza);
		redAtt.addFlashAttribute("successMessage", "Pizza successfully added.");
		return "redirect:/pizzas";
	}
	
	// GET (update) // uguale a create solo che modifica esistente invece di nuova Pizza
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true); // per far capire alla form se è un create o un update
		model.addAttribute("pizza", service.getById(id)); // passa pizza esistente a partire da id
		model.addAttribute("ingredientList", ingredientService.findAllSortByIngredient()); // passa tutti gli ingredienti
		return "/pizzas/edit";
	}
	
	// POST (update)
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza,
			BindingResult bindingResult, Model model, RedirectAttributes redAtt) {
		// se ci sono errori ritorna alla form
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredientList", ingredientService.findAllSortByIngredient());
			return "/pizzas/edit";
		}
		// se non ci sono errori salva i dati
		service.save(formPizza);
		redAtt.addFlashAttribute("successMessage", "Pizza info has been updated.");
		return "redirect:/pizzas";
	}
	
	// delete
	@GetMapping("delete/{id}")
	public String doDelete(@PathVariable("id") Integer id, RedirectAttributes redAtt) {
		service.deleteById(id);
		redAtt.addFlashAttribute("successMessage", "The pizza has been deleted.");
		return "redirect:/pizzas";
	}
	
}
