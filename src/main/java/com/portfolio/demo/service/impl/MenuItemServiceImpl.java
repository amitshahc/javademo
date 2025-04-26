package com.portfolio.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.portfolio.demo.dto.MenuItemDTO;
import com.portfolio.demo.entity.MenuItem;
import com.portfolio.demo.exception.ResourceNotFoundException;
import com.portfolio.demo.repository.MenuItemRepository;
import com.portfolio.demo.service.MenuItemService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Builder
public class MenuItemServiceImpl implements MenuItemService {

	private final MenuItemRepository menuItemRepository;

	@Override
	public MenuItemDTO createMenuItem(MenuItemDTO dto) {
		// TODO Auto-generated method stub

		MenuItem menuItem = MenuItem.builder().title(dto.getTitle()).description(dto.getDescription())
				.price(dto.getPrice()).build();

		MenuItem saved = menuItemRepository.save(menuItem);

		return mapToDTO(saved);
	}

	@Override
	public MenuItemDTO getMenuItem(Long id) {
		// TODO Auto-generated method stub
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));

		return mapToDTO(menuItem);
	}

	@Override
	public List<MenuItemDTO> getAllMenuItems() {
		// TODO Auto-generated method stub
		return menuItemRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public MenuItemDTO updateMenuItem(Long id, MenuItemDTO dto) {
		// TODO Auto-generated method stub
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found"));

		menuItem.setTitle(dto.getTitle());
		menuItem.setDescription(dto.getDescription());
		menuItem.setPrice(dto.getPrice());

		MenuItem updated = menuItemRepository.save(menuItem);

		return mapToDTO(updated);

	}

	@Override
	public void deleteMenuItem(Long id) {
		// TODO Auto-generated method stub
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));
//		menuItemRepository.deleteById(id);
		menuItemRepository.delete(menuItem);
	}

	private MenuItemDTO mapToDTO(MenuItem item) {
		return MenuItemDTO.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription())
				.price(item.getPrice()).build();
	}

}
