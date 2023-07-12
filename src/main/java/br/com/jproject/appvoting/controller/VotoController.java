package br.com.jproject.appvoting.controller;


import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import br.com.jproject.appvoting.services.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votar")
@CrossOrigin(origins = "*")
public class VotoController {
    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping("/{idPauta}/votos")
    public ResponseEntity<VotoDTO> receberVoto(@PathVariable Long idPauta, @RequestBody VotoDTO voto) {
        VotoDTO resultado = votoService.votar(voto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/{idPauta}/resultado")
    public ResponseEntity<ResultadoVotacao> obterResultadoVotacao(@PathVariable Long idPauta) {
        ResultadoVotacao resultado = votoService.obterResultadoVotacao(idPauta);
        return ResponseEntity.ok(resultado);
    }
}
