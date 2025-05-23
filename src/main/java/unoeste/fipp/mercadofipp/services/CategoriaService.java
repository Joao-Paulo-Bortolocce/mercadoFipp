package unoeste.fipp.mercadofipp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.mercadofipp.entities.Categoria;
import unoeste.fipp.mercadofipp.repositories.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAll(){
        return categoriaRepository.findAll();
    }

    public Categoria getById(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria){
        try{
            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean delete(long id){
        try {
            categoriaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
