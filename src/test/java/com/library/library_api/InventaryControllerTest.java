package com.library.library_api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.library.library_api.controller.InventaryController;
import com.library.library_api.model.Inventary;
import com.library.library_api.service.InventaryService;

@WebMvcTest(InventaryController.class)
public class InventaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventaryService inventaryService;

    @Test
    void getAllInventaries_ShouldReturnList() throws Exception {
        when(inventaryService.getAllInventaries()).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(get("/api/v1/inventary/"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookInventary_ShouldReturnInventary() throws Exception {
        Inventary inv = new Inventary();
        inv.setBookId(1L);
        
        when(inventaryService.getBookInventary(1L)).thenReturn(ResponseEntity.ok(inv));

        mockMvc.perform(get("/api/v1/inventary/{bookId}", 1L))
                .andExpect(status().isOk());
    }
}