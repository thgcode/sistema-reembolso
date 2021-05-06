package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.NotaFiscalForaDaValidadeException;
import br.com.zup.sistemareembolso.exceptions.NotaFiscalNaoExistenteException;
import br.com.zup.sistemareembolso.models.NotaFiscal;
import br.com.zup.sistemareembolso.repositories.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class NotaFiscalService {
    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

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

}