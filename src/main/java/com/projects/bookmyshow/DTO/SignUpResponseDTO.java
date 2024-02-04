package com.projects.bookmyshow.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDTO {
    private Long userId;
    private ResponseStatus responseStatus;
}