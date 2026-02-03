package br.com.ifba.feiraplus.features.feira.dto.response;


import lombok.Data;



@Data
public class FeiraBuscaResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private int espacos;
    private String tipoFeira;
    private Float nota;

}