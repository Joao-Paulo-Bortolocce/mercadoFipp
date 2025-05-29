package unoeste.fipp.mercadofipp.restcontrollers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.mercadofipp.entities.Categoria;
import unoeste.fipp.mercadofipp.entities.Erro;
import unoeste.fipp.mercadofipp.security.JWTTokenProvider;
import unoeste.fipp.mercadofipp.services.CategoriaService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("apis/categoria")
public class CategoriaRestController {
    @Autowired
    private CategoriaService categoriaService;

    public boolean verificaPermissao(String token) {
        if(token!= null && token.startsWith("Bearer "))
            token = token.substring(7);

        Claims dados = JWTTokenProvider.getAllClaimsFromToken(token);
        String level = dados.get("level", String.class);
        if(!level.equals("1"))
            return false;
        return true;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<Categoria> categoriaList= categoriaService.getAll();

        if(categoriaList!=null && !categoriaList.isEmpty()){
            return ResponseEntity.ok(categoriaList);
        }
        return ResponseEntity.badRequest().body(new Erro("Categorias não encontradas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id")long id, @RequestHeader("Authorization") String token){
        if(!verificaPermissao(token))
            return  ResponseEntity.badRequest().body(new Erro("Usuario sem permissão"));
       Categoria categoria= categoriaService.getById(id);
        if(categoria!=null){
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.badRequest().body(new Erro("Não há categoria cadastrada com o id: "+id));
    }

    @PostMapping
    public ResponseEntity<Object> inserir(@RequestBody Categoria categoria, @RequestHeader("Authorization") String token){

        if(!verificaPermissao(token))
            return  ResponseEntity.badRequest().body(new Erro("Usuario sem permissão"));
        if(categoriaService.save(categoria)!=null)
            return ResponseEntity.ok(categoria);
        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar uma nova categoria"));
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody Categoria categoria, @RequestHeader("Authorization") String token){
        if(!verificaPermissao(token))
            return  ResponseEntity.badRequest().body(new Erro("Usuario sem permissão"));
        if(categoriaService.save(categoria)!=null)
            return ResponseEntity.ok(categoria);
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar uma nova categoria"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable long id, @RequestHeader("Authorization") String token){
        if(!verificaPermissao(token))
            return  ResponseEntity.badRequest().body(new Erro("Usuario sem permissão"));
        if(categoriaService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body("Erro ao deletar categoria!");
    }
}
