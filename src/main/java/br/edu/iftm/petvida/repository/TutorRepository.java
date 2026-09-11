package br.edu.iftm.petvida.repository;

import br.edu.iftm.petvida.model.Tutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TutorRepository {

    private final JdbcTemplate jdbc;

    public TutorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void salvar(Tutor tutor) {
        String sql = "INSERT INTO tutor (id_tutor, nome, telefone) VALUES (?, ?, ?)";
        jdbc.update(sql, tutor.getId(), tutor.getNome(), tutor.getTelefone());
    }
}