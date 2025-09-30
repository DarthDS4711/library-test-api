package com.library.library_api.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventary {
    

    private Long id;

    private Long bookId;

    private int numberAvalable;
}
