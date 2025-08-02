package com.mvc_crud.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mvc_crud.Dto.UserDto;

public interface UserService {
	// Save User
	UserDto savedUser(UserDto userDto);
	
	// View User List of UserDto
	List<UserDto> getAllUsers();
	
	public Page<UserDto> getAllPaginationData(Pageable pageable);
	
	// Update User
	UserDto updateUser(UserDto userDto);
	
	// Get user base by id
	UserDto getUserById(long id);
	
	// Delete User
	void deleteUser(long id);
}
