package unoeste.fipp.mercadofipp.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import unoeste.fipp.mercadofipp.entities.Anuncio;
import unoeste.fipp.mercadofipp.entities.Erro;
import unoeste.fipp.mercadofipp.services.AnuncioService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("apis/anuncio")
public class AnuncioRestController {
    @Autowired
    AnuncioService anuncioService;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<Anuncio> anuncios= anuncioService.getAll();
        if(anuncios!=null && !anuncios.isEmpty())
            return ResponseEntity.ok(anuncios);
        return ResponseEntity.badRequest().body(new Erro("Anuncios não encontrados"));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAll(@PathVariable(name = "id") Long id){
        Anuncio anuncio= anuncioService.getById(id);
        if(anuncio!=null )
            return ResponseEntity.ok(anuncio);
        return ResponseEntity.badRequest().body(new Erro("Anuncio não encontrados"));
    }

    @GetMapping("add-pergunta/{id}/{texto}")
    public ResponseEntity<Object>  addPergunta(@PathVariable(name = "id") Long idAnuncio,@PathVariable(name = "texto") String texto){
        if (anuncioService.addPergunta(idAnuncio, texto))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a pergunta"));
    }

    @PostMapping("add-foto/{id}")
    public ResponseEntity<Object>  addFoto(@PathVariable(name = "id") Long idAnuncio, @RequestBody MultipartFile fotos[]){
        if (anuncioService.addFoto(idAnuncio, fotos))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a Foto"));
    }

    @GetMapping("add-resposta/{id}/{texto}")
    public ResponseEntity<Object>  addResposta(@PathVariable(name = "id") Long idPergunta,@PathVariable(name = "texto") String texto){
        if (anuncioService.addResposta(idPergunta, texto))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a Resposta"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<Object> save(@RequestPart("anuncio") Anuncio anuncio,  @RequestPart("fotos") MultipartFile fotos[]){
        Anuncio novoAnuncio= anuncioService.add(anuncio,fotos);
        if(novoAnuncio!=null)
            return ResponseEntity.ok(novoAnuncio);
        return ResponseEntity.badRequest().body(new Erro("Não foi possivel adicionar o anuncio"));
    }

    @PutMapping
    public  ResponseEntity<Object> update(@RequestBody Anuncio anuncio){
        Anuncio novoAnuncio= anuncioService.add(anuncio,null);
        if(novoAnuncio!=null)
            return ResponseEntity.ok(novoAnuncio);
        return ResponseEntity.badRequest().body(new Erro("Não foi possivel alterar o anuncio"));
    }
}
