package com.appresent.domain.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.appresent.domain.constants.ErrorConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PearsonDTO {

	private Long id;
	@NotBlank(message = ErrorConstants.ERROR_NAME_REQUIRED)
	private String name;
	@Valid
	@NotNull(message = ErrorConstants.ERROR_GROUP_REQUIRED)
	private GroupDTO group;
	@Valid
	@NotNull(message = ErrorConstants.ERROR_FUNCTIONS_REQUIRED)
	private List<FunctionDTO> functions;
}
