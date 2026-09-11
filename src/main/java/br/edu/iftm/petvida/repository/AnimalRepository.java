package br.edu.iftm.petvida.repository;

import br.edu.iftm.petvida.model.Animal;
import br.edu.iftm.petvida.model.Tutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnimalRepository {

    private final JdbcTemplate jdbc;

    public AnimalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // UMA consulta com JOIN
    public Animal buscarPorId(int id) {
        String sql = """
            SELECT a.id_animal, a.nome AS animal_nome, a.especie, a.idade,
                   t.id_tutor, t.nome AS tutor_nome, t.telefone
            FROM animal a
            JOIN tutor t ON t.id_tutor = a.tutor_id_tutor
            WHERE a.id_animal = ?
            """;

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            Tutor tutor = new Tutor(
                rs.getInt("id_tutor"),
                rs.getString("tutor_nome"),
                rs.getString("telefone")
            );
            return new Animal(
                rs.getInt("id_animal"),
                rs.getString("animal_nome"),
                rs.getString("especie"),
                rs.getInt("idade"),
                tutor
            );
        }, id);
    }

    public int contarAnimais() {
        String sql = "SELECT COUNT(*) FROM animal";
        return jdbc.queryForObject(sql, Integer.class);
    }

    public double mediaIdade() {
        // CAST força a média com decimais
        String sql = "SELECT CAST(AVG(idade) AS DOUBLE) FROM animal";
        return jdbc.queryForObject(sql, Double.class);
    }

    public String animalMaisVelho() {
        String sql = "SELECT nome FROM animal ORDER BY idade DESC LIMIT 1";
        return jdbc.queryForObject(sql, String.class);
    }

    public int contarAnimaisDoTutor(int idTutor) {
        String sql = "SELECT COUNT(*) FROM animal WHERE tutor_id_tutor = ?";
        return jdbc.queryForObject(sql, Integer.class, idTutor);
    }

    public void salvar(Animal animal) {
        String sql = "INSERT INTO animal (id_animal, nome, especie, idade, tutor_id_tutor) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
            animal.getId(),
            animal.getNome(),
            animal.getEspecie(),
            animal.getIdade(),
            animal.getTutor().getId()
        );
    }
}