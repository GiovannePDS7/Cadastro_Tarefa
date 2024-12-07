package com.example.Cadastrar_Tarefa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Tarefa")
    private Long idTarefa;

    @Column(name = "Nome_Tarefa", nullable = false, length = 100)
    private String nomeTarefa;

    @Column(name = "Fornecedor", length = 100)
    private String fornecedor;

    @Column(name = "Valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Evento", referencedColumnName = "Id_Evento", nullable = false)
    private Evento evento;

}

