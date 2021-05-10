package br.com.zup.sistemareembolso.config;

import org.hibernate.annotations.Cache;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notafiscal")
public class ConfiguracaoDaImagemDaNotaFiscal {
    private String diretorioParaUpload;

    public String getDiretorioParaUpload() {
        return diretorioParaUpload;
    }

    public void setDiretorioParaUpload(String diretorioParaUpload) {
        this.diretorioParaUpload = diretorioParaUpload;
    }
}