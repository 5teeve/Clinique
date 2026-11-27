package com.projet;

import com.projet.config.SpringContext;
import com.projet.model.Patient;
import com.projet.repository.PatientRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            PatientRepository repository = SpringContext.getBean(PatientRepository.class);
            List<Patient> patients = repository.findAll();

            System.out.println("Patients en base : " + patients.size());
            for (Patient patient : patients) {
                System.out.printf("  #%d  %-20s %s%n",
                        patient.getId(), patient.getNom(), patient.getTelephone());
            }
        } finally {
            SpringContext.fermer();
        }
    }
}
