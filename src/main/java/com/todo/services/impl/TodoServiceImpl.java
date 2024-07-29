package com.todo.services.impl;

import com.todo.model.Todos;
import com.todo.repository.TodoRepo;
import com.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepo todoRepo;

    @Autowired
    public TodoServiceImpl(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @Override
    public Todos createTodo(Todos todo) {
        return todoRepo.save(todo);
    }

    @Override
    public Todos updateTodo(Integer id, String newTitle, String newDescription) {
        Todos todo = getTodoById(id);
        todo.setTitle(newTitle);
        todo.setDescription(newDescription);
        return todoRepo.save(todo);
    }

    @Override
    public void deleteTodo(Integer id) {
        Todos todo = getTodoById(id);
        todoRepo.delete(todo);
    }

    @Override
    public List<Todos> getAllTodo() {
        return todoRepo.findAll();
    }

    private Todos getTodoById(Integer id) {
        Optional<Todos> todoOptional = todoRepo.findById(id);
        return todoOptional.orElseThrow(() -> new RuntimeException("Todo not found for id: " + id));
    }
}
