package com.example.homework2.todo.service;

import com.example.homework2.member.entity.Member;
import com.example.homework2.member.repository.MemberRepository;
import com.example.homework2.todo.dto.TodoResponseDto;
import com.example.homework2.todo.dto.TodoSaveRequestDto;
import com.example.homework2.todo.dto.TodoSaveResponseDto;
import com.example.homework2.todo.dto.TodoUpdateRequestDto;
import com.example.homework2.todo.entity.Todo;
import com.example.homework2.todo.repository.TodoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto save(Long memberId, TodoSaveRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("해당 id를 가진 회원은 존재하지 않습니다.")
        );

        Todo todo = new Todo(dto.getContent(), member);
        Todo savedTodo = todoRepository.save(todo);

        return new TodoSaveResponseDto(savedTodo.getId(), savedTodo.getContent(),
                member.getId(), member.getEmail());
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll() {
        List<Todo> todos = todoRepository.findAll();

        List<TodoResponseDto> dtos = new ArrayList<>();

        for (Todo todo : todos) {
            dtos.add(new TodoResponseDto(todo.getId(), todo.getContent()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 todo를 조회하지 못 했습니다.")
        );
        return new TodoResponseDto(todo.getId(), todo.getContent());
    }

    @Transactional
    public TodoResponseDto update(Long memberId, Long todoId, TodoUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("해당 id를 가진 회원은 존재하지 않습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 todo를 조회하지 못 했습니다.")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("해당 게시물에 접근 권한이 존재 하지 않습니다.");
        }
        todo.update(dto.getContent());
        return new TodoResponseDto(todo.getId(), todo.getContent());
    }

    public void deleteById(Long memberId, Long todoId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("해당 id를 가진 회원은 존재하지 않습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 todo를 조회하지 못 했습니다.")
        );
        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("해당 게시물에 접근 권한이 존재 하지 않습니다.");
        }
        todoRepository.deleteById(todoId);
     }
}
