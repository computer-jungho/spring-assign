package com.example.homework2.todo.controller;

import com.example.homework2.common.consts.Const;
import com.example.homework2.todo.dto.TodoResponseDto;
import com.example.homework2.todo.dto.TodoSaveRequestDto;
import com.example.homework2.todo.dto.TodoSaveResponseDto;
import com.example.homework2.todo.dto.TodoUpdateRequestDto;
import com.example.homework2.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todos")
    public TodoSaveResponseDto save (
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody TodoSaveRequestDto dto) {
        return todoService.save(memberId, dto);
    }

    @GetMapping("/todos")
    public List<TodoResponseDto> getAll () {
        return todoService.findAll();
    }

    @GetMapping("/todos/{todoId}")
    public TodoResponseDto getOne (@PathVariable Long todoId) {
        return todoService.findById(todoId);
    }

    @PutMapping("/todos/{todoId}")
    public TodoResponseDto update (
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequestDto dto) {
        return todoService.update(memberId, todoId, dto);
    }

    @DeleteMapping("/todos/{todoId}")
    public void delete (
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId) {
        todoService.deleteById(memberId, todoId);
    }
}
