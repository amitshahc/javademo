package com.portfolio.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.demo.dto.MenuItemDTO;
import com.portfolio.demo.service.MenuItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

	private final MenuItemService menuItemService;

	@PostMapping
	public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
		MenuItemDTO createdItem = menuItemService.createMenuItem(menuItemDTO);
		return ResponseEntity.ok(createdItem);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
		MenuItemDTO menuItemDTO = menuItemService.getMenuItem(id);
		return ResponseEntity.ok(menuItemDTO);
	}

	@GetMapping
	public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
		List<MenuItemDTO> menuItems = menuItemService.getAllMenuItems();
		return ResponseEntity.ok(menuItems);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id,
			@Valid @RequestBody MenuItemDTO menuItemDTO) {
		MenuItemDTO updatedItem = menuItemService.updateMenuItem(id, menuItemDTO);
		return ResponseEntity.ok(updatedItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
		menuItemService.deleteMenuItem(id);
		return ResponseEntity.noContent().build();
	}

}
