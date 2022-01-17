package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Pizza;
import org.generation.italy.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository repo;
	
	public List<Pizza> findAllSortByName() {
		return repo.findAll(Sort.by("name"));
	}
	
	// search
	public List<Pizza> findByKeywordSortedById(String keyword) {
		return repo.findByNameContainingIgnoreCaseOrderById(keyword);
	}
	
	public Pizza save(Pizza p) {
		return repo.save(p);
	}
	
	public Pizza getById(Integer id) {
		return repo.getById(id);
	}
	
	// delete
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}
	
}
