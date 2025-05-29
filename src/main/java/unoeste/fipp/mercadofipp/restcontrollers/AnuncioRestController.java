package unoeste.fipp.mercadofipp.restcontrollers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import unoeste.fipp.mercadofipp.entities.Anuncio;
import unoeste.fipp.mercadofipp.entities.Erro;
import unoeste.fipp.mercadofipp.entities.Usuario;
import unoeste.fipp.mercadofipp.security.JWTTokenProvider;
import unoeste.fipp.mercadofipp.services.AnuncioService;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/anuncio")


public class AnuncioRestController {
    @Autowired
    AnuncioService anuncioService;

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
        List<Anuncio> anuncios= anuncioService.getAll();
        if(anuncios!=null && !anuncios.isEmpty())
            return ResponseEntity.ok(anuncios);
        return ResponseEntity.badRequest().body(new Erro("Anuncios não encontrados"));
    }

    @GetMapping(value = "destaque")
    public ResponseEntity<Object> getDestaque() {
        List<Anuncio> anuncios = anuncioService.getDestaque();
        if(anuncios != null)
            return ResponseEntity.ok(anuncios);
        return ResponseEntity.badRequest().body(new Erro("Erro ao procurar anúncios!"));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Object> getByUser(@PathVariable(name = "id") Long id){
        List<Anuncio> anuncios= anuncioService.getByUserId(id);
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

    @PostMapping("add-pergunta/{id}")
    public ResponseEntity<Object>  addPergunta(@PathVariable(name = "id") Long idAnuncio, @RequestBody Map pergunta){
        if (anuncioService.addPergunta(idAnuncio, (String) pergunta.get("texto")))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a pergunta"));
    }

    @PostMapping("add-foto/{id}")
    public ResponseEntity<Object>  addFoto(@PathVariable(name = "id") Long idAnuncio, @RequestBody MultipartFile fotos[]){
        if (anuncioService.addFoto(idAnuncio, fotos))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a Foto"));
    }

    @PostMapping("add-resposta/{id}")
    public ResponseEntity<Object>  addResposta(@PathVariable(name = "id") Long idPergunta, @RequestBody Map resposta){
        if (anuncioService.addResposta(idPergunta, (String) resposta.get("texto")))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar a Resposta"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<Object> save(@RequestPart("anuncio") Anuncio anuncio,  @RequestPart("fotos") MultipartFile fotos[], @RequestHeader("Authorization")String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims dados = JWTTokenProvider.getAllClaimsFromToken(token);
        Long id = dados.get("idUser",Long.class);
        anuncio.setUsuario(new Usuario(id));
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delAnuncio(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if(!verificaPermissao(token))
            return ResponseEntity.badRequest().body(new Erro("Sem permissão"));
        if (anuncioService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao apagar categoria!"));
    }
}
