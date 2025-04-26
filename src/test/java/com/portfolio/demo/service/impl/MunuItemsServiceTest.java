package com.portfolio.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.demo.dto.MenuItemDTO;
import com.portfolio.demo.entity.MenuItem;
import com.portfolio.demo.repository.MenuItemRepository;

@ExtendWith(MockitoExtension.class)
class MunuItemsServiceTest {

	@Mock
	private MenuItemRepository menuItemRepository;

	@InjectMocks
	private MenuItemServiceImpl menuItemService;

	public void MenuItemServiceTest() {
		MockitoAnnotations.openMocks(this); // initializes mocks
	}

	@Test
	void testCreateMenuItem() {
		// Arrange: Prepare DTO and expected Entity
		MenuItemDTO dto = new MenuItemDTO();
		dto.setTitle("Test Title");
		dto.setDescription("Test Description");

		MenuItem mockSaved = new MenuItem();
		mockSaved.setId(1L);
		mockSaved.setTitle("Test Title");
		mockSaved.setDescription("Test Description");

		// Mock behavior of repository.save()
		when(menuItemRepository.save(any(MenuItem.class))).thenReturn(mockSaved);

		// Act
		MenuItemDTO saved = menuItemService.createMenuItem(dto);

		// Assert
		assertNotNull(saved);
		assertEquals("Test Title", saved.getTitle());
		assertEquals("Test Description", saved.getDescription());

		verify(menuItemRepository, times(1)).save(any(MenuItem.class));
	}

	@Test
	void testGetMenuItemById() {
		// Arrange
		MenuItem item = MenuItem.builder().id(2L).title("Sample").description("Sample Desc").build();

		when(menuItemRepository.findById(2L)).thenReturn(Optional.of(item));

		// Act
		MenuItemDTO result = menuItemService.getMenuItem(2L);

		// Assert
		assertEquals("Sample", result.getTitle());
		verify(menuItemRepository).findById(2L);
	}
}
