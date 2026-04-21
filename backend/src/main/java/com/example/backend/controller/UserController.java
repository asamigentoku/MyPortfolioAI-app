package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("create")
    public UserDto save(@RequestBody UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.save(user));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
