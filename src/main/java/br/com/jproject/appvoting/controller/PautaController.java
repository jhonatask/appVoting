package br.com.jproject.appvoting.controller;

import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import br.com.jproject.appvoting.services.PautaService;
import br.com.jproject.appvoting.services.SessaoVotacaoService;
import br.com.jproject.appvoting.services.VotoService;
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

        @GetMapping
        public ResponseEntity<List<Pauta>> listarPautas() {
            List<Pauta> pautas = pautaService.listarPautas();
            return ResponseEntity.ok(pautas);
        }
}
