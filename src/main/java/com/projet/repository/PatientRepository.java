package com.projet.repository;

import com.projet.model.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {

    private static final RowMapper<Patient> PATIENT_ROW_MAPPER = (rs, rowNum) -> new Patient(
            rs.getInt("id"),
            rs.getString("nom"),
            rs.getString("telephone"));

    private final JdbcTemplate jdbcTemplate;

    public PatientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient> findAll() {
        return jdbcTemplate.query(
                "SELECT id, nom, telephone FROM patient ORDER BY id",
                PATIENT_ROW_MAPPER);
    }

    public Optional<Patient> findById(int id) {
        return jdbcTemplate.query(
                "SELECT id, nom, telephone FROM patient WHERE id = ?",
                PATIENT_ROW_MAPPER,
                id)
                .stream()
                .findFirst();
    }

    public int count() {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM patient", Integer.class);
        return total == null ? 0 : total;
    }

    public int insert(Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO patient (nom, telephone) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getTelephone());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("Aucun identifiant genere pour le patient insere.");
        }
        return generatedId.intValue();
    }

    public int update(Patient patient) {
        return jdbcTemplate.update(
                "UPDATE patient SET nom = ?, telephone = ? WHERE id = ?",
                patient.getNom(),
                patient.getTelephone(),
                patient.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM patient WHERE id = ?", id);
    }
}
