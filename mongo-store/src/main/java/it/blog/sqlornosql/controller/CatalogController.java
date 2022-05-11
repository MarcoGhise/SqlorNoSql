package it.blog.sqlornosql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.blog.sqlornosql.bean.Catalog;
import it.blog.sqlornosql.dao.CatalogDao;

@Controller
public class CatalogController {
	
	@Autowired
	CatalogDao catalogDao;

	@GetMapping("/catalog/{basketId}")
	public String getBasket(@PathVariable String basketId, Model model) {

		List<Catalog> rows = catalogDao.getCatalog();
				
		model.addAttribute("size", rows.size());
		
		model.addAttribute("catalog",rows);
		
		model.addAttribute("basketId", basketId);

		return "catalog";
	}
}
