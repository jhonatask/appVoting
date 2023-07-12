package br.com.jproject.appvoting.controller;

import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import br.com.jproject.appvoting.services.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    void testReceberVoto() throws Exception {

        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdPauta(1L);
        votoDTO.setCpf("12345678900");
        votoDTO.setVoto("SIM");


        VotoDTO createdVotoDTO = new VotoDTO();
        createdVotoDTO.setIdPauta(1L);
        createdVotoDTO.setCpf("12345678900");
        createdVotoDTO.setVoto("SIM");


        when(votoService.votar(any(VotoDTO.class))).thenReturn(createdVotoDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/votar/{idPauta}/votos", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"12345678900\",\"voto\":\"SIM\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPauta").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("12345678900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.voto").value("SIM"));
    }

    @Test
    void testObterResultadoVotacao() throws Exception {

        Long id = 1L;
        ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
        Pauta pauta = new Pauta();
        resultadoVotacao.setPauta(pauta);
        resultadoVotacao.setTotalVotos(10);
        resultadoVotacao.setTotalVotosSim(7);
        resultadoVotacao.setTotalVotosNao(3);

        when(votoService.obterResultadoVotacao(id)).thenReturn(resultadoVotacao);

        mockMvc.perform(MockMvcRequestBuilders.get("/votar/{idPauta}/resultado", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalVotos").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalVotosSim").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalVotosNao").value(3));
    }
}
