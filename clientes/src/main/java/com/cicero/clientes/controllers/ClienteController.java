package com.cicero.clientes.controllers;

import com.cicero.clientes.model.entity.Cliente;
import com.cicero.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente salvar(@RequestBody Cliente cliente){
       return repository.save(cliente);
   }

   @GetMapping("/{id}")
   public Cliente acharPorId(@PathVariable Integer id){
       return repository.findById(id)
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    public void atualizarCLiente(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado){
       repository.findById(id)
               .map( cliente -> {
                   clienteAtualizado.setId(cliente.getId());
                   return repository.save(clienteAtualizado);
               })
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
   }
}
