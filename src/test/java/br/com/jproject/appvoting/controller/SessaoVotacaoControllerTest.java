package br.com.jproject.appvoting.controller;

import br.com.jproject.appvoting.services.SessaoVotacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SessaoVotacaoController.class)
public class SessaoVotacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void testAbrirSessaoVotacao() throws Exception {

        Long idPauta = 1L;
        Integer duracaoMinutos = 60;

        mockMvc.perform(MockMvcRequestBuilders.post("/sessao/{idPauta}/sessoes", idPauta)
                        .param("duracao", duracaoMinutos.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Sessão de votação aberta com sucesso"));
    }
}
