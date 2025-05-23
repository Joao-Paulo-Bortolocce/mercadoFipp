package unoeste.fipp.mercadofipp.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.mercadofipp.entities.Categoria;
import unoeste.fipp.mercadofipp.entities.Erro;
import unoeste.fipp.mercadofipp.services.CategoriaService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("apis/categoria")
public class CategoriaRestController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<Categoria> categoriaList= categoriaService.getAll();
        if(categoriaList!=null && !categoriaList.isEmpty()){
            return ResponseEntity.ok(categoriaList);
        }
        return ResponseEntity.badRequest().body(new Erro("Categorias não encontradas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id")long id){
       Categoria categoria= categoriaService.getById(id);
        if(categoria!=null){
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.badRequest().body(new Erro("Não há categoria cadastrada com o id: "+id));
    }

    @PostMapping
    public ResponseEntity<Object> inserir(@RequestBody Categoria categoria){
        if(categoriaService.save(categoria)!=null)
            return ResponseEntity.ok(categoria);
        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar uma nova categoria"));
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody Categoria categoria){
        if(categoriaService.save(categoria)!=null)
            return ResponseEntity.ok(categoria);
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar uma nova categoria"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable long id){
        if(categoriaService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body("Erro ao deletar categoria!");
    }
}
