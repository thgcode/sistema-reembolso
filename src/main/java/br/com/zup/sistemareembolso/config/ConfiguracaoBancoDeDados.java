package br.com.zup.sistemareembolso.config;

import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import br.com.zup.sistemareembolso.repositories.LocalidadeRepository;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("!test")
public class ConfiguracaoBancoDeDados implements ApplicationRunner {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!categoriaRepository.existsByDescricao("Eletrônicos")) {
            Categoria categoria = new Categoria();
            categoria.setDescricao("Eletrônicos");
            categoriaRepository.save(categoria);
        }

        Localidade localidade;

        if (!localidadeRepository.existsByNome("Uberlândia")) {
            localidade = new Localidade();
            localidade.setNome("Uberlândia");
            localidadeRepository.save(localidade);
        } else {
            localidade = localidadeRepository.findByNome("Uberlândia").get();
        }

        if (!projetoRepository.existsByNomeDoProjeto("Catalisa")) {
            Projeto projeto = new Projeto();
            projeto.setNomeDoProjeto("Catalisa");
            projeto.setCodigoDoProjeto("CT");
            projeto.setLocalidade(localidade);
            projeto.setVerba(50000);
            projetoRepository.save(projeto);
        }
    }
}
