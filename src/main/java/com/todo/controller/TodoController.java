package com.todo.controller;

import com.todo.model.Todos;
import com.todo.services.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todos")
@RestController
@CrossOrigin
public class TodoController {
    @Autowired
    private TodoServiceImpl todoService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Todos Todocreate( @RequestBody Todos todos) {
        return todoService.createTodo(todos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Todos> Todoupdate(@PathVariable Integer id, @RequestBody Todos todo) {
        Todos updatedTodo = todoService.updateTodo(id, todo.getTitle(), todo.getDescription());
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void Tododelete(@PathVariable Integer id) {
        todoService.deleteTodo(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<Todos> getAllTodo() {
        return todoService.getAllTodo();
    }
}
