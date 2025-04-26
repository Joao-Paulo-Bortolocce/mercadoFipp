package unoeste.fipp.mercadofipp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.mercadofipp.entities.Usuario;
import unoeste.fipp.mercadofipp.repositories.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<Usuario> getAll(){
        return usuarioRepo.findAll();
    }

    public Usuario getById(long id){
        return usuarioRepo.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        try{
            Usuario novoUsuario=usuarioRepo.save(usuario);
            return novoUsuario;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(long id){
        Usuario usuario= usuarioRepo.findById(id).orElse(null);
        try{
            usuarioRepo.delete(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
