package com.projet.service;

import com.projet.model.Patient;
import com.projet.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> tous() {
        return repository.findAll();
    }

    public int total() {
        return repository.count();
    }

    public Optional<Patient> parId(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public Patient creer(Patient patient) {
        valider(patient);
        int id = repository.insert(patient);
        patient.setId(id);
        return patient;
    }

    public Patient modifier(Patient patient) {
        if (patient.getId() <= 0) {
            throw new IllegalArgumentException("Identifiant du patient invalide.");
        }
        valider(patient);

        int lignes = repository.update(patient);
        if (lignes == 0) {
            throw new IllegalArgumentException("Aucun patient ne correspond a l'identifiant " + patient.getId() + ".");
        }
        return patient;
    }

    public void supprimer(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Identifiant du patient invalide.");
        }
        int lignes = repository.deleteById(id);
        if (lignes == 0) {
            throw new IllegalArgumentException("Aucun patient ne correspond a l'identifiant " + id + ".");
        }
    }

    private void valider(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Les informations du patient sont manquantes.");
        }
        if (patient.getNom() == null || patient.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du patient est obligatoire.");
        }
        if (patient.getTelephone() == null || patient.getTelephone().isBlank()) {
            throw new IllegalArgumentException("Le telephone du patient est obligatoire.");
        }
        patient.setNom(patient.getNom().trim());
        patient.setTelephone(patient.getTelephone().trim());
    }
}
