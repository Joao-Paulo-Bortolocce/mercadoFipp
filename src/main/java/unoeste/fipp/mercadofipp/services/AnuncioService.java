package unoeste.fipp.mercadofipp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.mercadofipp.entities.Anuncio;
import unoeste.fipp.mercadofipp.repositories.AnuncioRepository;

import java.util.List;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<Anuncio> getAll() {
        return anuncioRepository.findAll();
    }

    public Anuncio add(Anuncio anuncio, MultipartFile fotos[]) {
        Anuncio novoAnuncio = anuncioRepository.save(anuncio);
        if(fotos!=null && fotos.length>0 && novoAnuncio!=null){
            addFoto(novoAnuncio.getId(),fotos);
        }
        return novoAnuncio;
    }

    public boolean addPergunta(Long id_anuncio, String texto) {
        try {
            anuncioRepository.addPergunta(texto, id_anuncio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addResposta(Long id_pergunta, String texto) {
        try {
            anuncioRepository.addResposta(texto, id_pergunta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addFoto(Long id_anuncio, MultipartFile fotos[]) {
        try {
            for (int i = 0; i < fotos.length; i++) {
                anuncioRepository.addFoto(fotos[i].getBytes(), id_anuncio, pegaExtensao(fotos[i].getOriginalFilename()));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String pegaExtensao(String name) {
        String ext;
        int pos = name.lastIndexOf(".");
        ext = name.substring(pos+1);
        return ext;
    }

    public List<Anuncio> getDestaque() {
        return anuncioRepository.findTop5ByOrderByDataDesc();
    }

    public Anuncio getById(Long id) {
        return anuncioRepository.findById(id).orElse(null);
    }

    public List<Anuncio> getByUserId(Long id) {
        return anuncioRepository.getByUsuarioId(id);
    }

    public boolean delete(long id) {
        Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
        try {
            anuncioRepository.delFoto(anuncio.getId());
            anuncioRepository.delPergunta(anuncio.getId());
            anuncioRepository.delete(anuncio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
