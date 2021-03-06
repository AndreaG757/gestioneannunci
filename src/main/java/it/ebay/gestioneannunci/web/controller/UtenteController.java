package it.ebay.gestioneannunci.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.ebay.gestioneannunci.model.EditParam;
import it.ebay.gestioneannunci.model.InsertParam;
import it.ebay.gestioneannunci.model.StatoUtente;
import it.ebay.gestioneannunci.model.Utente;
import it.ebay.gestioneannunci.service.ruolo.RuoloService;
import it.ebay.gestioneannunci.service.utente.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;
	@Autowired 
	private RuoloService ruoloService;
	@Autowired

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", utenti);
		mv.setViewName("utente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("list_stati_attr", StatoUtente.values());
		model.addAttribute("ruoloAttr", ruoloService.listAll());
		return "utente/search";
	}
	
	@PostMapping("/list")
	public String listUtenti(Utente utenteExample, ModelMap model) {
		List<Utente> utenti = utenteService.findByExample(utenteExample);
		for(Utente app:utenti) {
			System.out.println(app.getCognome()+app.getNome());
		}
		model.addAttribute("utente_list_attribute", utenti);
		return "utente/list";
	}

	@GetMapping("/insert")
	public String createUtente(Model model) {
		Utente utenteInstance= new Utente();
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setDateCreated(new Date());
		model.addAttribute("insert_utente_attr", utenteInstance);
		return "utente/insert";
	}

	@PostMapping("/save")
	public String saveUtente(@RequestParam(name = "passwordnuova") String passwordnuova, @Validated({InsertParam.class}) @ModelAttribute("insert_utente_attr") Utente utente,
			BindingResult result,Model model, RedirectAttributes redirectAttrs) {
		
		 if (utente.getPassword() != null && !utente.getPassword().equals(passwordnuova)) {
	            result.rejectValue("password", "Fdsfdsf.sdfsdf", "Le due password non coincidono");
	        }
	        if (result.hasErrors()) {
	            return "utente/insert";
	        }

	        utente.setDateCreated(new Date());
	        utente.setStato(StatoUtente.CREATO);
	        utenteService.inserisciNuovo(utente);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}
	
	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.invertUserAbilitation(idUtente);
		return "redirect:/utente";
	}
	
	@GetMapping("/show/{idUtente}")
	public String showRegisti(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtente(idUtente));
		return "utente/show";
	}
	
	@GetMapping("/edit/{idUtente}")
	public String editUtente(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utente=utenteService.caricaUtenteConRuoli(idUtente);
		model.addAttribute("utente_attribute", utente);
		model.addAttribute("stati_attr", StatoUtente.values());
		model.addAttribute("ruoloAttr",ruoloService.listAll());
		return "utente/edit";
	}
	
	@PostMapping("/saveEdit/")
	public String executeUpdateUtente(
			@Validated({EditParam.class}) @ModelAttribute("utente_attribute") Utente utente, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("ruoloAttr", ruoloService.listAll());
			model.addAttribute("stati_attr", StatoUtente.values());
			return "utente/edit";
		}
		utenteService.aggiorna(utente);
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";

	}
	
	
	
	
}
