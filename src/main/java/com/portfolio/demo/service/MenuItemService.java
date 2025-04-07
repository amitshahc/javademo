package com.portfolio.demo.service;

import java.util.List;

import com.portfolio.demo.dto.MenuItemDTO;

public interface MenuItemService {

	MenuItemDTO createMenuItem(MenuItemDTO dto);

	MenuItemDTO getMenuItem(Long id);

	List<MenuItemDTO> getAllMenuItems();

	MenuItemDTO updateMenuItem(Long id, MenuItemDTO dto);

	void deleteMenuItem(Long id);
}
