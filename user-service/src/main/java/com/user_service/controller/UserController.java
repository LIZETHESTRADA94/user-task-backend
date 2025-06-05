package com.user_service.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.model.dto.UserDTO;
import com.user_service.model.entity.User;
import com.user_service.service.IUserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

	@Autowired
    private IUserService service;

    @GetMapping
	public List<User> getUsers() {
		return service.getAllUsers();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {

		UserDTO user = service.getUser(userId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search")
    public ResponseEntity<List<User>> search(@RequestParam String query) {

		List<User> users = service.search(query);

		if (users != null) {
			return ResponseEntity.ok(users);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {

        User createdUser = service.createUser(userDTO);
		if (createdUser != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {

        User createdUser = service.updateUser(userId, userDTO);
		if (createdUser != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

		service.deleteUser(userId);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/header")
    public ResponseEntity<Map<String, Object>> hetHeader(
		@RequestHeader Map<String, String> requestHeader,
        HttpServletResponse response	
	) {
            
        Map<String, Object> headersJson = new HashMap<>();
        headersJson.put("RequestHeader", requestHeader);
        
        return ResponseEntity.ok(headersJson);
    }

}
