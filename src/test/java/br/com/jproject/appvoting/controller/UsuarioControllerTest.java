package br.com.jproject.appvoting.controller;


import br.com.jproject.appvoting.dto.UsuarioDTO;
import br.com.jproject.appvoting.services.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testCadastrarUsuario() throws Exception {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setNome("John Doe");

        UsuarioDTO createdUsuarioDTO = new UsuarioDTO();
        createdUsuarioDTO.setCpf("12345678900");
        createdUsuarioDTO.setNome("John Doe");

        when(usuarioService.criarUsuario(any(UsuarioDTO.class))).thenReturn(createdUsuarioDTO);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"12345678900\",\"nome\":\"John Doe\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("12345678900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }
}
