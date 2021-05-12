package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.notafiscal.entrada.ImagemDTO;
import br.com.zup.sistemareembolso.dtos.notafiscal.saida.NotaFiscalSaidaDTO;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.NotaFiscal;
import br.com.zup.sistemareembolso.services.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("notasfiscais/")
public class NotaFiscalController {
    @Autowired
    private NotaFiscalService notaFiscalService;

    @GetMapping("imagem/{nomeDoArquivo:.+}")
    public ResponseEntity<Resource> baixarImagem(@PathVariable String nomeDoArquivo, HttpServletRequest request) {
        Resource resource = notaFiscalService.carregarImagem(nomeDoArquivo);

        String tipoDoArquivo = null;
        try {
            tipoDoArquivo = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }

        if (tipoDoArquivo == null) {
            tipoDoArquivo = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(tipoDoArquivo))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("imagem/")
    public ImagemDTO subirImagem(@RequestParam("arquivo") MultipartFile arquivo) {
        String nomeDoArquivo = notaFiscalService.armazenarImagem(arquivo);

        String linkDeDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(nomeDoArquivo).toUriString();

        return new ImagemDTO(nomeDoArquivo, linkDeDownload, arquivo.getContentType(), arquivo.getSize());
    }

    @GetMapping("{codigoDaNota}/")
    public NotaFiscalSaidaDTO pesquisarNotaFiscalPeloCodigo(@PathVariable int codigoDaNota) {
        NotaFiscal notaFiscal = notaFiscalService.pesquisarNotaFiscal(codigoDaNota);
        double valor = notaFiscalService.calcularValorDaNotaPeloId(codigoDaNota);

        return NotaFiscalSaidaDTO.converterDTODeNotaFiscalEValor(notaFiscal, valor);
    }

    @GetMapping
    public Iterable <NotaFiscal> listarTodasNotasFiscais() {
        return notaFiscalService.listarNotasFiscais();
    }
}
