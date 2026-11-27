package com.projet.controller;

import com.projet.config.SpringContext;
import com.projet.model.Patient;
import com.projet.repository.PatientRepository;
import com.projet.service.PatientService;
import mg.itu.framework.annotation.Controller;
import mg.itu.framework.annotation.Json;
import mg.itu.framework.annotation.UrlMapping;
import mg.itu.framework.vue.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController() {
        PatientRepository repository = SpringContext.getBean(PatientRepository.class);
        this.patientService = new PatientService(repository);
    }

    @UrlMapping(url = "/")
    public ModelAndView accueil() {
        ModelAndView mav = new ModelAndView("home");
        try {
            mav.addAttribute("total", patientService.total());
        } catch (RuntimeException e) {
            mav.addAttribute("erreur", "Base de donnees indisponible : " + e.getMessage());
        }
        return mav;
    }

    @UrlMapping(url = "/patients")
    public ModelAndView liste() {
        return vueListe(null, false);
    }

    @UrlMapping(url = "/patients/new")
    public ModelAndView nouveau() {
        return vueFormulaire(new Patient(), "create", null);
    }

    @UrlMapping(url = "/patients", method = "POST")
    public ModelAndView enregistrer(Patient patient) {
        try {
            patientService.creer(patient);
            return vueListe("Patient ajoute avec succes.", false);
        } catch (IllegalArgumentException e) {
            return vueFormulaire(patient, "create", e.getMessage());
        } catch (RuntimeException e) {
            return vueListe("Erreur lors de l'enregistrement : " + e.getMessage(), true);
        }
    }

    @UrlMapping(url = "/patients/show")
    public ModelAndView detail(String id) {
        try {
            Optional<Patient> patient = patientService.parId(identifiant(id));
            if (patient.isEmpty()) {
                return vueListe("Patient introuvable.", true);
            }
            ModelAndView mav = new ModelAndView("patient-detail");
            mav.addAttribute("patient", patient.get());
            return mav;
        } catch (IllegalArgumentException e) {
            return vueListe(e.getMessage(), true);
        } catch (RuntimeException e) {
            return vueListe("Erreur lors de la lecture : " + e.getMessage(), true);
        }
    }

    @UrlMapping(url = "/patients/edit")
    public ModelAndView modifier(String id) {
        try {
            Optional<Patient> patient = patientService.parId(identifiant(id));
            if (patient.isEmpty()) {
                return vueListe("Patient introuvable.", true);
            }
            return vueFormulaire(patient.get(), "edit", null);
        } catch (IllegalArgumentException e) {
            return vueListe(e.getMessage(), true);
        } catch (RuntimeException e) {
            return vueListe("Erreur lors de la lecture : " + e.getMessage(), true);
        }
    }

    @UrlMapping(url = "/patients/update", method = "POST")
    public ModelAndView mettreAJour(Patient patient) {
        if (patient == null || patient.getId() <= 0) {
            return vueListe("Identifiant du patient manquant.", true);
        }
        try {
            patientService.modifier(patient);
            return vueListe("Patient modifie avec succes.", false);
        } catch (IllegalArgumentException e) {
            return vueFormulaire(patient, "edit", e.getMessage());
        } catch (RuntimeException e) {
            return vueListe("Erreur lors de la modification : " + e.getMessage(), true);
        }
    }

    @UrlMapping(url = "/patients/delete", method = "POST")
    public ModelAndView supprimer(String id) {
        try {
            patientService.supprimer(identifiant(id));
            return vueListe("Patient supprime avec succes.", false);
        } catch (IllegalArgumentException e) {
            return vueListe(e.getMessage(), true);
        } catch (RuntimeException e) {
            return vueListe("Erreur lors de la suppression : " + e.getMessage(), true);
        }
    }

    /**
     * API JSON utilisee plus tard par le programme autonome (QR codes).
     */
    @Json
    @UrlMapping(url = "/api/patients")
    public List<Patient> apiPatients() {
        return patientService.tous();
    }

    private ModelAndView vueListe(String message, boolean erreur) {
        ModelAndView mav = new ModelAndView("patients");
        try {
            List<Patient> patients = patientService.tous();
            mav.addAttribute("patients", patients);
            mav.addAttribute("total", patients.size());
        } catch (RuntimeException e) {
            mav.addAttribute("erreur", "Impossible de lire les patients : " + e.getMessage());
        }
        if (message != null) {
            mav.addAttribute(erreur ? "erreur" : "message", message);
        }
        return mav;
    }

    private ModelAndView vueFormulaire(Patient patient, String mode, String erreur) {
        ModelAndView mav = new ModelAndView("patient-form");
        mav.addAttribute("patient", patient);
        mav.addAttribute("mode", mode);
        if (erreur != null) {
            mav.addAttribute("erreur", erreur);
        }
        return mav;
    }

    private int identifiant(String valeur) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Identifiant du patient manquant.");
        }
        try {
            int id = Integer.parseInt(valeur.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("Identifiant du patient invalide.");
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Identifiant du patient invalide : " + valeur);
        }
    }
}
