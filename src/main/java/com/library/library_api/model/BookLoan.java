package com.library.library_api.model;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookLoan {
    

    private Long id;
    private Long studentId;
    private boolean isReturned;
    private LocalDateTime initLoanDate;
    private LocalDateTime finishedLoanDate;
    private String commentaries;
    private Long bookId;
}
