package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.config.ConfiguracaoDaImagemDaNotaFiscal;
import br.com.zup.sistemareembolso.exceptions.ArmazenarArquivoException;
import br.com.zup.sistemareembolso.exceptions.CaminhoDoArquivoInvalidoException;
import br.com.zup.sistemareembolso.exceptions.NotaFiscalForaDaValidadeException;
import br.com.zup.sistemareembolso.exceptions.NotaFiscalNaoExistenteException;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.NotaFiscal;
import br.com.zup.sistemareembolso.repositories.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class NotaFiscalService {
    private NotaFiscalRepository notaFiscalRepository;
    private DespesaService despesaService;
    private ConfiguracaoDaImagemDaNotaFiscal configuracaoDaImagemDaNotaFiscal;
    private Path localDasImagensDasNotas;

    @Autowired
    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository, DespesaService despesaService, ConfiguracaoDaImagemDaNotaFiscal configuracaoDaImagemDaNotaFiscal) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.despesaService = despesaService;
        this.configuracaoDaImagemDaNotaFiscal = configuracaoDaImagemDaNotaFiscal;

        localDasImagensDasNotas = Paths.get(configuracaoDaImagemDaNotaFiscal.getDiretorioParaUpload()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(localDasImagensDasNotas);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Não foi possível criar o diretório em que os arquivos enviados serão armazenados.", ex);
        }
    }

    public String armazenarImagem(MultipartFile file) {
        String nomeDoArquivo = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (nomeDoArquivo.contains("..") || nomeDoArquivo.contains("/")) {
                throw new CaminhoDoArquivoInvalidoException(nomeDoArquivo);
            }

            // Copia o arquivo para o local de destino (Substituindo arquivo existente)
            Path local = localDasImagensDasNotas.resolve(nomeDoArquivo);
            Files.copy(file.getInputStream(), local, StandardCopyOption.REPLACE_EXISTING);

            return nomeDoArquivo;
        } catch (IOException ex) {
            throw new ArmazenarArquivoException(nomeDoArquivo, ex);
        }
    }

    public Resource carregarImagem(String nomeDoArquivo) {
        try {
            Path caminhoDaImagem = localDasImagensDasNotas.resolve(nomeDoArquivo).normalize();
            Resource resource = new UrlResource(caminhoDaImagem.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado " + nomeDoArquivo);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Arquivo não encontrado " + nomeDoArquivo, ex);
        }
    }

    private void validarNotaFiscal(NotaFiscal notaFiscal) {
        if (notaFiscal.getDataDeEmissao().isBefore(LocalDate.now().minusDays(30))) {
            throw new NotaFiscalForaDaValidadeException();
        }
    }

    public NotaFiscal adicionarNotaFiscal(NotaFiscal notaFiscal) {
        validarNotaFiscal(notaFiscal);

        return notaFiscalRepository.save(notaFiscal);
    }

    public Iterable <NotaFiscal> listarNotasFiscais() {
        return notaFiscalRepository.findAll();
    }

    public NotaFiscal pesquisarNotaFiscal(int codigoDaNota) {
        Optional<NotaFiscal> optionalNotaFiscal = notaFiscalRepository.findById(codigoDaNota);

        if (optionalNotaFiscal.isPresent()) {
            return optionalNotaFiscal.get();
        }

        throw new NotaFiscalNaoExistenteException();
    }

    public void excluirNotaFiscalPeloCodigo(int codigoDaNota) {
        NotaFiscal notaFiscal = pesquisarNotaFiscal(codigoDaNota);

        notaFiscalRepository.delete(notaFiscal);
    }

    public double calcularValorDaNotaPeloId(int codigoDaNota) {
        double valor = 0;

        pesquisarNotaFiscal(codigoDaNota);

        for (Despesa despesa: despesaService.pesquisarDespesasPeloCodigoDaNotaFiscal(codigoDaNota)) {
            valor += despesa.getValor();
        }

        return valor;
    }

}