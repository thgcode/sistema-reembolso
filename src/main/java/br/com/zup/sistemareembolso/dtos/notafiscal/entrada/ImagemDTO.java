package br.com.zup.sistemareembolso.dtos.notafiscal.entrada;

public class ImagemDTO {
    private String nomeDoArquivo;
    private String linkDeDownload;
    private String tipoDoArquivo;
    private long tamanho;

    public ImagemDTO(String nomeDoArquivo, String linkDeDownload, String tipoDoArquivo, long tamanho) {
        this.nomeDoArquivo = nomeDoArquivo;
        this.linkDeDownload = linkDeDownload;
        this.tipoDoArquivo = tipoDoArquivo;
        this.tamanho = tamanho;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public void setNomeDoArquivo(String nomeDoArquivo) {
        this.nomeDoArquivo = nomeDoArquivo;
    }

    public String getLinkDeDownload() {
        return linkDeDownload;
    }

    public void setLinkDeDownload(String linkDeDownload) {
        this.linkDeDownload = linkDeDownload;
    }

    public String getTipoDoArquivo() {
        return tipoDoArquivo;
    }

    public void setTipoDoArquivo(String tipoDoArquivo) {
        this.tipoDoArquivo = tipoDoArquivo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }
}
