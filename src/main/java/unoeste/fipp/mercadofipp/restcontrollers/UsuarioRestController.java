package unoeste.fipp.mercadofipp.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.mercadofipp.entities.Usuario;
import unoeste.fipp.mercadofipp.services.UsuarioService;

import java.util.List;

public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<Object> getUsuarios(){
        List<Usuario> usuarioList;
        usuarioList = usuarioService.getAll();
        if (usuarioList.isEmpty()) {
            return ResponseEntity.badRequest().body("Sem dados");
        }
        return ResponseEntity.ok(usuarioList);



    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioId(@PathVariable(name = "id") Long id){
        Usuario usuario= usuarioService.getById(id);
        if (usuario==null)
            return ResponseEntity.badRequest().body("Usuário não encontrado!");
        return ResponseEntity.ok(usuario);
    }

//    @GetMapping("get-by-name/{name}")
//    public ResponseEntity<Object> getUsuarioName(@PathVariable(name = "name") String nome){
//        Usuario usuario= usuarioRepo.findByNome(nome);
//        if (usuario==null)
//                return ResponseEntity.badRequest().body("Usuário não encontrado!");
//        return ResponseEntity.ok(usuario);
//    }

    @PostMapping
    public ResponseEntity<Object> addUsuario(@RequestBody Usuario usuario){
        Usuario novoUsuario= usuarioService.save(usuario);
        if (novoUsuario!=null)
            return ResponseEntity.ok(usuario);
        return ResponseEntity.badRequest().body("Erro ao gravar usuario");
    }

    @PutMapping
    public ResponseEntity<Object> updUsuario(@RequestBody Usuario usuario){

        Usuario novoUsuario= usuarioService.save(usuario);
        if (novoUsuario!=null)
            return ResponseEntity.ok(usuario);
        return ResponseEntity.badRequest().body("Erro ao alterar usuario");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delUsuario(@PathVariable long id){
        if(usuarioService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body("Erro ao deletar o usuario: ");
    }
}
