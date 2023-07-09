package br.com.jproject.appvoting.controller;

import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import br.com.jproject.appvoting.services.PautaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pautas")
@CrossOrigin(origins = "*")
public class PautaController {
        private final PautaService pautaService;

        public PautaController(PautaService pautaService) {
            this.pautaService = pautaService;
        }

        @PostMapping
        public ResponseEntity<PautaDTO> cadastrarPauta(@RequestBody PautaDTO pauta) {
            PautaDTO pautaCadastrada = pautaService.cadastrarPauta(pauta);
            return ResponseEntity.status(HttpStatus.CREATED).body(pautaCadastrada);
        }

        @PostMapping("/{idPauta}/sessoes")
        public ResponseEntity<String> abrirSessaoVotacao(@PathVariable Long idPauta,
                                                         @RequestParam("duracao") Integer duracaoMinutos) {
            pautaService.abrirSessaoVotacao(idPauta, duracaoMinutos);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sessão de votação aberta com sucess");
        }

        @PostMapping("/{idPauta}/votos")
        public ResponseEntity<VotoDTO> receberVoto(@PathVariable Long idPauta, @RequestBody VotoDTO voto) {
            VotoDTO resultado = pautaService.votar(voto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        }

        @GetMapping("/{idPauta}/resultado")
        public ResponseEntity<ResultadoVotacao> obterResultadoVotacao(@PathVariable Long idPauta) {
            ResultadoVotacao resultado = pautaService.obterResultadoVotacao(idPauta);
            return ResponseEntity.ok(resultado);
        }

        @GetMapping
        public ResponseEntity<List<Pauta>> listarPautas() {
            List<Pauta> pautas = pautaService.listarPautas();
            return ResponseEntity.ok(pautas);
        }
}
