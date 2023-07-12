package br.com.jproject.appvoting.controller;


import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.services.PautaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @Test
    public void testCadastrarPauta() throws Exception {
        PautaDTO pauta = new PautaDTO();
        pauta.setTitulo("Pauta de Teste");
        pauta.setDescricao("Descrição da Pauta de Teste");

        when(pautaService.cadastrarPauta(pauta)).thenReturn(pauta);

        mockMvc.perform(MockMvcRequestBuilders.post("/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Pauta de Teste\",\"descricao\":\"Descrição da Pauta de Teste\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").exists());
    }

    @Test
    public void testGetPautas() throws Exception{
        List<Pauta> pautas = new ArrayList<>();
        Pauta pauta1 = new Pauta();
        pauta1.setId(1L);
        pauta1.setTitulo("Pauta 1");
        pauta1.setDescricao("Descrição da Pauta 1");
        pautas.add(pauta1);

        Pauta pauta2 = new Pauta();
        pauta2.setId(2L);
        pauta2.setTitulo("Pauta 2");
        pauta2.setDescricao("Descrição da Pauta 2");
        pautas.add(pauta2);

        when(pautaService.listarPautas()).thenReturn(pautas);

        mockMvc.perform(MockMvcRequestBuilders.get("/pautas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Pauta 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Descrição da Pauta 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("Pauta 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value("Descrição da Pauta 2"));
    }

}
