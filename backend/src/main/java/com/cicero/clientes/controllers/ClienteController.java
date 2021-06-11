package com.cicero.clientes.controllers;

import com.cicero.clientes.model.entity.Cliente;
import com.cicero.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping
    public List<Cliente> obterClientes(){
        return repository.findAll();
    }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente salvar(@RequestBody @Valid Cliente cliente){
       return repository.save(cliente);
   }

   @GetMapping("/{id}")
   public Cliente acharPorId(@PathVariable Integer id){
       return repository.findById(id)
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontrado"));
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(@PathVariable Integer id){
       repository.findById(id)
               .map( cliente -> {
                   repository.delete(cliente);
                   return Void.TYPE;
               })
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
   }

   @PutMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCLiente(@PathVariable Integer id, @RequestBody @Valid  Cliente clienteAtualizado){
       repository.findById(id)
               .map( cliente -> {
                   clienteAtualizado.setId(cliente.getId());
                   return repository.save(clienteAtualizado);
               })
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
   }
}
