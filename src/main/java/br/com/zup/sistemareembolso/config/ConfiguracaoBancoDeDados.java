package br.com.zup.sistemareembolso.config;

import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import br.com.zup.sistemareembolso.repositories.LocalidadeRepository;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoBancoDeDados implements ApplicationRunner {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!categoriaRepository.existsByDescricao("Eletrônicos")) {
            Categoria categoria = new Categoria();
            categoria.setDescricao("Eletrônicos");
            categoriaRepository.save(categoria);
        }
        
        Localidade localidade = new Localidade();
        localidade.setNome("Uberlândia");

    }
}
