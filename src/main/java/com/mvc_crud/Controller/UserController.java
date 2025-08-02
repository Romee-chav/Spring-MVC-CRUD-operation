package com.mvc_crud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc_crud.Dto.UserDto;
import com.mvc_crud.Service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("userDto", new UserDto());
		return "create";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute UserDto userDto,BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("userDto", userDto);
			return "create";
		}
		service.savedUser(userDto);
		return "redirect:/view";
	}

//	@GetMapping("/view")
//	public String read(Model model) {
//		List<UserDto> dtoList = service.getAllUsers();
//		model.addAttribute("userList", dtoList);
//		return "viewAll";
//	}

	// View All records with pagination
	@GetMapping("/view")
	public String getAllUserWithPage(Model model, @RequestParam(defaultValue = "0") int pageNo) {
		// Show how Many records in page
		int pageSize = 4;
		// If PageNumber is less 0 then set pageNumber is 0
		if (pageNo < 0) {
			pageNo = 0;
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<UserDto> allPaginationData = service.getAllPaginationData(pageable);
		model.addAttribute("userPage", allPaginationData);
		model.addAttribute("currentPage", pageNo);
		return "viewAll";
	}

	@GetMapping("/edit/{id}")
	public String showEditPage(@PathVariable Long id, Model model) {
		UserDto dto = service.getUserById(id);
		model.addAttribute("userDto", dto);
		return "edit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute UserDto userDto) {

		service.updateUser(userDto);
		return "redirect:/view";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.deleteUser(id);
		return "redirect:/view";
	}
}
