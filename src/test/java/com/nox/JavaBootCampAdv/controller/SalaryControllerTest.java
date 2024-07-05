package com.nox.JavaBootCampAdv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nox.JavaBootCampAdv.dto.PaymentRequestDto;
import com.nox.JavaBootCampAdv.dto.SalaryPaymentDto;
import com.nox.JavaBootCampAdv.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalaryController.class)
class SalaryControllerTest {
    @MockBean
    SalaryService salaryService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private SalaryController salaryController;

    @Test
    void testPayMonthlyPayments() throws Exception {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setYear(2024);
        request.setMonth(Month.JANUARY);
        SalaryPaymentDto response = new SalaryPaymentDto();
        List<SalaryPaymentDto> responseList = Collections.singletonList(response);
        when(salaryService.payMonthlyPayments(request)).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/salaryPayments/payout")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(salaryService).payMonthlyPayments(request);
        verifyNoMoreInteractions(salaryService);
    }

    @Test
    void testPayMonthlyPayments_withBadRequest() throws Exception {
        PaymentRequestDto request = new PaymentRequestDto();

        SalaryPaymentDto response = new SalaryPaymentDto();
        List<SalaryPaymentDto> responseList = Collections.singletonList(response);
        when(salaryService.payMonthlyPayments(request)).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/salaryPayments/payout")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(salaryService);
    }

    @Test
    void testPayMonthlyPayments_withInternalError() throws Exception {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setYear(2024);
        request.setMonth(Month.JANUARY);
        request.setThrowIfPaid(true);

        when(salaryService.payMonthlyPayments(request)).thenThrow(new RuntimeException("Expected exception"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/salaryPayments/payout")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Expected exception"));

        verify(salaryService).payMonthlyPayments(request);
        verifyNoMoreInteractions(salaryService);
    }

    @Test
    void testGetPaymentsByEmployee() throws Exception {
        Long id = 1L;
        Integer year = 2024;
        Month month = Month.JANUARY;
        SalaryPaymentDto response = new SalaryPaymentDto();
        List<SalaryPaymentDto> responseList = Collections.singletonList(response);
        when(salaryService.getPaymentsByEmployee(id, year, month)).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/salaryPayments/employee/{id}", id)
                        .param("year", year.toString())
                        .param("month", month.toString()))
                .andExpect(status().isOk());

        verify(salaryService).getPaymentsByEmployee(id, year, month);
        verifyNoMoreInteractions(salaryService);
    }
}