package br.com.jproject.appvoting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pauta")
public class Pauta {

    public static final String ABERTA = "A";
    public static final String ENCERRADA = "E";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String titulo;
    @Column(nullable = false, length = 1000)
    private String descricao;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_encerramento")
    private LocalDateTime dataEncerramento;
    @Column(nullable = false)
    private String status;
}
