package com.io.badri.vehicles.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.io.badri.vehicles.models.Country;
import com.io.badri.vehicles.services.CountryService;

@Controller
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping("/countries")
	public String getCountries(Model model) {
		List<Country> countryList = countryService.getCountries();
		
		model.addAttribute("countries", countryList);
		return "Country";
	}
	@GetMapping("/countries/showFormForAdd") 
	public String showFormForAdd(Model model){
		Country country = new Country();
		
	 model.addAttribute("country", country);
	 return "CountryForm";
	}
	
	@PostMapping("/countries/addNew")
	//country is in th:object = 
	public String addCountry(@ModelAttribute("country") Country country) {
		countryService.addCountry(country);
        
		//redirect to the list and prevent duplicate submission.
		return "redirect:/countries";
	}
	
	@GetMapping("/countries/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("countryId") int countryId, Model model) {
		   // get the country for that country id
            // System.out.println("update" + countryId);
		   Optional<Country> country = countryService.getCountryById(countryId);		    
		   
		   //set country as model to pre-populate the form
			model.addAttribute("country", country);
		
		   //send to form
		   return "CountryForm";
	}
	
	@GetMapping("/countries/deleteCountry")
	public String deleteCountry(@RequestParam("countryId") int countryId, Model model) {
		   System.out.println("delete" + countryId);
		   Optional<Country> country = countryService.getCountryById(countryId);		    
		   
		   if(country !=null) {
			   countryService.deleteCountry(countryId);
		   }
		
		return "redirect:/countries";
	}
}
