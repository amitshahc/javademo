package com.portfolio.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDTO {

	private Long id;

	@NotNull
	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title must be less than 100 characters")
	private String title;

	@NotBlank(message = "Description is required")
	@Size(max = 255, message = "Description must be less than 255 characters")
	private String description;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private double price;
}
