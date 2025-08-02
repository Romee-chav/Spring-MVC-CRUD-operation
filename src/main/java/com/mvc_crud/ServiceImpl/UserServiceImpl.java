package com.mvc_crud.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mvc_crud.Dto.UserDto;
import com.mvc_crud.Entities.User;
import com.mvc_crud.Repository.UserRepo;
import com.mvc_crud.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo repo;

	@Autowired
	private ModelMapper mapper;

	// Save User
	@Override
	public UserDto savedUser(UserDto userDto) {
		// DTO object to entity object conversion using Helper Class
		User user = mapper.map(userDto, User.class);
		User savedEntity = repo.save(user);
		// Convert save entity agan to DTO
		UserDto dto = mapper.map(savedEntity, UserDto.class);
		return dto;
	}

	// Show All Users
	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = repo.findAll();
		// Conversion of List of entity to List of DTO Using Stream Api
		List<UserDto> dtoList = userList.stream().map(entity -> mapper.map(entity, UserDto.class))
				.collect(Collectors.toList());
		return dtoList;
	}

	// Get User by Id
	@Override
	public UserDto getUserById(long id) {
		User user = repo.findById(id).orElse(null);
		// Convert Entity to DTO
		UserDto dto = mapper.map(user, UserDto.class);
		return dto;
	}

	// Update User
	@Override
	public UserDto updateUser(UserDto userDto) {

		User userEntity = repo.findById(userDto.getId()).orElse(null);
		if (userEntity != null) {
			// take new data from DTO and set to entity
			userEntity.setName(userDto.getName());
			userEntity.setEmail(userDto.getEmail());
			userEntity.setAddress(userDto.getAddress());
			User savedEntity = repo.save(userEntity);
			UserDto dto = mapper.map(savedEntity, UserDto.class);
			return dto;
		}
		return null;
	}

	// Delete User By Id
	@Override
	public void deleteUser(long id) {
		repo.deleteById(id);
	}

	// Show Data with Pagination
	@Override
	public Page<UserDto> getAllPaginationData(Pageable pageable) {
		Page<User> page = repo.findAll(pageable);
		// Convert entity page into dto page
		Page<UserDto> dtoPage = page.map(user -> mapper.map(user,UserDto.class));
		return dtoPage;
	}
}
