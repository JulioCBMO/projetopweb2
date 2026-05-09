package pweb2.quizz.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String processarLogin(@RequestParam String nome, HttpSession session, RedirectAttributes flash) {
        if (nome == null || nome.trim().isEmpty()) {
            flash.addFlashAttribute("erro", "O nome de usuário é obrigatório.");   
            return "redirect:/login";
        }
        session.setAttribute("participante", nome.trim());
        return "redirect:/lobby";
    }

}
