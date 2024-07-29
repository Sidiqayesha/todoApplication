package com.todo.services;

import com.todo.model.Todos;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface TodoService {

    Todos createTodo(Todos todos);

    public Todos updateTodo(Integer id, String newTitle, String newDescription);

    void deleteTodo(Integer id);

    List<Todos> getAllTodo();


}
