package pweb2.quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import pweb2.quizz.Repository.CorridaRepository;
import pweb2.quizz.Repository.PerguntaRepository;
import pweb2.quizz.Ui.NavPageBuilder;
import pweb2.quizz.model.Corrida;
import pweb2.quizz.model.Pergunta;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    // O MÉTODO MANUAL @GetMapping("/login") FOI DELETADO DAQUI!

    @GetMapping("/corridas")
    public String listarCorridas(@RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Corrida> pagina = corridaRepository.findAll(pageable);
            model.addAttribute("corridas", pagina);
            model.addAttribute("navPage", NavPageBuilder.build(pagina));
            return "admin/lista-corridas";
    }

    @GetMapping("/corridas/nova")
    public String formNovaCorrida(Model model) {
        model.addAttribute("corrida", new Corrida());
        return "admin/form-corrida";
    }

    @GetMapping("/corridas/{id}/editar")
    public String formEditarCorrida(@PathVariable Long id, Model model) {
        Corrida corrida = corridaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + id));
        model.addAttribute("corrida", corrida);
        return "admin/form-corrida";
    }

    @PostMapping("/corridas/salvar")
    public String salvarCorrida(Corrida corrida, RedirectAttributes flash) {
        if (corrida.getTitulo() == null || corrida.getTitulo().trim().isEmpty()) {
            flash.addFlashAttribute("erro", "O título é obrigatório.");
            return "redirect:/admin/corridas/nova";
        }
        if (corrida.getTempoSegundos() == null || corrida.getTempoSegundos() <= 0) {
            flash.addFlashAttribute("erro", "O tempo deve ser maior que zero.");
            return "redirect:/admin/corridas/nova";
        }
        if (corrida.getAtiva() == null) {
            corrida.setAtiva(false);
        }
        corridaRepository.save(corrida);
        flash.addFlashAttribute("mensagem", "Corrida salva com sucesso!");
        return "redirect:/admin/corridas";
    }

    @GetMapping("/corridas/{id}/excluir")
    public String excluirCorrida(@PathVariable Long id, RedirectAttributes flash) {
        corridaRepository.deleteById(id);
        flash.addFlashAttribute("mensagem", "Corrida excluída com sucesso!");
        return "redirect:/admin/corridas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas")
    public String listarPerguntas(@PathVariable Long corridaId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Pergunta> pagina = perguntaRepository.findByCorridaId(corridaId, pageable);
        model.addAttribute("corrida", corrida);
        model.addAttribute("perguntas", pagina);
        model.addAttribute("navPage", NavPageBuilder.build(pagina));
        return "admin/lista-perguntas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas/nova")
    public String formNovaPergunta(@PathVariable Long corridaId, Model model) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        Pergunta pergunta = new Pergunta();
        pergunta.setCorrida(corrida);
        model.addAttribute("pergunta", pergunta);
        model.addAttribute("corridaId", corridaId);
        return "admin/form-pergunta";
    }

    @PostMapping("/corridas/{corridaId}/perguntas/salvar")
    public String salvarPergunta(@PathVariable Long corridaId, Pergunta pergunta, 
                                 @RequestParam(value = "arquivoImagem", required = false) MultipartFile arquivoImagem, 
                                 RedirectAttributes flash) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        pergunta.setCorrida(corrida);
        
        if (pergunta.getId() != null) {
            Pergunta existente = perguntaRepository.findById(pergunta.getId()).orElse(null);
            if (existente != null && existente.getImagemBase64() != null) {
                pergunta.setImagemBase64(existente.getImagemBase64());
            }
        }

        if (arquivoImagem != null && !arquivoImagem.isEmpty()) {
            try {
                String base64Image = Base64.getEncoder().encodeToString(arquivoImagem.getBytes());
                pergunta.setImagemBase64(base64Image);
            } catch (Exception e) {
                flash.addFlashAttribute("erro", "Erro ao processar a imagem.");
                return "redirect:/admin/corridas/" + corridaId + "/perguntas";
            }
        }

        perguntaRepository.save(pergunta);
        flash.addFlashAttribute("mensagem", "Pergunta salva com sucesso!");
        return "redirect:/admin/corridas/" + corridaId + "/perguntas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas/{id}/excluir")
    public String excluirPergunta(@PathVariable Long corridaId, @PathVariable Long id, RedirectAttributes flash) {
        perguntaRepository.deleteById(id);
        flash.addFlashAttribute("mensagem", "Pergunta excluída com sucesso!");
        return "redirect:/admin/corridas/" + corridaId + "/perguntas";
    }
}