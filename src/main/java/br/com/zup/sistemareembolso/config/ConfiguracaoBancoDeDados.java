package br.com.zup.sistemareembolso.config;

import br.com.zup.sistemareembolso.models.*;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import br.com.zup.sistemareembolso.repositories.LocalidadeRepository;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!categoriaRepository.existsByDescricao("Eletrônicos")) {
            Categoria categoria = new Categoria();
            categoria.setDescricao("Eletrônicos");
            categoriaRepository.save(categoria);
        }

        Localidade localidade;
        Projeto projeto;

        if (!localidadeRepository.existsByNome("Uberlândia")) {
            localidade = new Localidade();
            localidade.setNome("Uberlândia");
            localidadeRepository.save(localidade);
        } else {
            localidade = localidadeRepository.findByNome("Uberlândia").get();
        }

        if (!projetoRepository.existsByNomeDoProjeto("Catalisa")) {
            projeto = new Projeto();
            projeto.setNomeDoProjeto("Catalisa");
            projeto.setCodigoDoProjeto("CT");
            projeto.setLocalidade(localidade);
            projeto.setVerba(50000);
            projetoRepository.save(projeto);
        } else {
            projeto = projetoRepository.findByNomeDoProjeto("Catalisa").get();
        }

        if (!colaboradorRepository.existsById("796.373.610-49")) {
            Colaborador administrador = new Colaborador();
            administrador.setCpf("796.373.610-49");
            administrador.setEmail("teste@teste.com");
            administrador.setSenha(bCryptPasswordEncoder.encode("teste123"));
            administrador.setNomeCompleto("Administrador do Sistema");
            administrador.setCargo(Cargo.DIRETOR);
            administrador.setProjeto(projeto);

            colaboradorRepository.save(administrador);
        }
    }
}
