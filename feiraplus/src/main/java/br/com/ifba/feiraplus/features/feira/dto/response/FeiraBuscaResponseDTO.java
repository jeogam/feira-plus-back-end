package br.com.ifba.feiraplus.features.feira.dto.response;

public class FeiraBuscaResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private int espacos;
    private String tipoFeira;

    // --- Getters e Setters Manuais (para substituir o @Data) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getEspacos() {
        return espacos;
    }

    public void setEspacos(int espacos) {
        this.espacos = espacos;
    }

    public String getTipoFeira() {
        return tipoFeira;
    }

    public void setTipoFeira(String tipoFeira) {
        this.tipoFeira = tipoFeira;
    }
}