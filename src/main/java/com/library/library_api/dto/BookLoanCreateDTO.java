package com.library.library_api.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookLoanCreateDTO {
    
    private int studentNumber;

    @NotNull
    private Long bookId;
}
