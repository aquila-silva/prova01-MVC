package br.edu.iftm.petvida;

import br.edu.iftm.petvida.model.Animal;
import br.edu.iftm.petvida.model.Tutor;
import br.edu.iftm.petvida.repository.AnimalRepository;
import br.edu.iftm.petvida.repository.TutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetvidaApplication implements CommandLineRunner {

    private final TutorRepository tutorRepo;
    private final AnimalRepository animalRepo;

    public PetvidaApplication(TutorRepository tutorRepo, AnimalRepository animalRepo) {
        this.tutorRepo = tutorRepo;
        this.animalRepo = animalRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(PetvidaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // --- Tutores da Seção 2 ---
        Tutor marina = new Tutor(1, "Marina Alves", "34 99101-0001");
        Tutor carlos = new Tutor(2, "Carlos Prado", "34 99101-0002");

        // --- SEU tutor (troque NN e nome) ---
        Tutor eu = new Tutor(142, "Aluno", "34 94242-4242");

        // Salva tutores ANTES dos animais (FK)
        tutorRepo.salvar(marina);
        tutorRepo.salvar(carlos);
        tutorRepo.salvar(eu);

        // --- Animais da Seção 2 ---
        animalRepo.salvar(new Animal(2, "Mimi", "gato", 3, marina));
        animalRepo.salvar(new Animal(3, "Thor", "cao", 1, carlos));
        animalRepo.salvar(new Animal(4, "Lila", "gato", 11, carlos));

        // --- SEU animal ---
        animalRepo.salvar(new Animal(142, "Pet_42", "cao", 42, eu));
    }
}