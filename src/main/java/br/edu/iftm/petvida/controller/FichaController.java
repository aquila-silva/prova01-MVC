package br.edu.iftm.petvida.controller;

import br.edu.iftm.petvida.model.Animal;
import br.edu.iftm.petvida.repository.AnimalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class FichaController {

    private final AnimalRepository animalRepo;

    public FichaController(AnimalRepository animalRepo) {
        this.animalRepo = animalRepo;
    }

    @GetMapping("/ficha_42")
    public String ficha(Model model) {
        Animal a = animalRepo.buscarPorId(142);
        model.addAttribute("nome", a.getNome());
        model.addAttribute("especie", a.getEspecie());
        model.addAttribute("idade", a.getIdade());
        model.addAttribute("tutorNome", a.getTutor().getNome());
        model.addAttribute("tutorTelefone", a.getTutor().getTelefone());
        return "ficha";
    }

    @GetMapping("/tutor_42")
    public String tutor(Model model) {
        Animal a = animalRepo.buscarPorId(142);   // pra pegar o tutor
        model.addAttribute("nome", a.getTutor().getNome());
        model.addAttribute("telefone", a.getTutor().getTelefone());
        model.addAttribute("qtd", animalRepo.contarAnimaisDoTutor(a.getTutor().getId()));
        return "tutor";
    }

    @GetMapping("/resumo_42")
    public String resumo(Model model) {
        int total = animalRepo.contarAnimais();
        double media = animalRepo.mediaIdade();
        String maisVelho = animalRepo.animalMaisVelho();

        String mediaFormatada = String.format(Locale.US, "%.2f", media);
        String agora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        model.addAttribute("total", total);
        model.addAttribute("media", mediaFormatada);
        model.addAttribute("maisVelho", maisVelho);
        model.addAttribute("dataHora", agora);
        return "resumo";
    }
}