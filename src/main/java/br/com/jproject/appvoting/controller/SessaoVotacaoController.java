package br.com.jproject.appvoting.controller;

import br.com.jproject.appvoting.services.SessaoVotacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessao")
@CrossOrigin(origins = "*")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    public SessaoVotacaoController(SessaoVotacaoService sessaoVotacaoService) {
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @PostMapping("/{idPauta}/sessoes")
    public ResponseEntity<String> abrirSessaoVotacao(@PathVariable Long idPauta,
                                                     @RequestParam("duracao") Integer duracaoMinutos) {
        sessaoVotacaoService.abrirSessaoVotacao(idPauta, duracaoMinutos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sessão de votação aberta com sucesso");
    }
}
