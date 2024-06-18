package com.nox.JavaBootCampAdv.service;

import com.github.javafaker.Faker;
import com.nox.JavaBootCampAdv.MappersConfiguration;
import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.entity.Company;
import com.nox.JavaBootCampAdv.mapper.CompanyMapper;
import com.nox.JavaBootCampAdv.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CompanyService.class,
        CompanyMapper.class
})
@SpringBootTest(classes = {MappersConfiguration.class})
class CompanyServiceTest {
    @MockBean
    private CompanyRepository companyRepository;
    @MockBean
    private Faker faker;
    @MockBean
    private CompanyMapper companyMapperMock;
    @Autowired
    private CompanyMapper companyMapper;

    @Test
    public void testGetAllCompanies() {
        CompanyService service = new CompanyService(companyRepository, companyMapper, faker);

        List<Company> companies = Arrays.asList(
                new Company(1L, "Company A", "Location A"),
                new Company(2L, "Company B", "Location B")
        );

        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        List<CompanyDto> actualDtos = service.getAllCompanies();
        assertEquals(companies.size(), actualDtos.size());
    }

    @Test
    public void testGetCompanyById_Success() {
        CompanyService service = new CompanyService(companyRepository, companyMapperMock, faker);
        Long id = 1L;
        Company company = new Company(1L, "Test Company", "Test Location");
        CompanyDto expectedDto = new CompanyDto(1L, "Test Company", "Test Location");

        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        Mockito.when(companyMapperMock.toDto(company)).thenReturn(expectedDto);
        Optional<CompanyDto> actualDto = service.getCompanyById(id);

        assertTrue(actualDto.isPresent());
        assertEquals(expectedDto, actualDto.get());
    }

    @Test
    public void testGetCompanyById_Failure() {
        CompanyService service = new CompanyService(companyRepository, companyMapper, faker);
        Long id = 1L;
        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CompanyDto> actualDto = service.getCompanyById(id);
        assertFalse(actualDto.isPresent());
    }

    @Test
    public void testCreateCompany() {
        CompanyService service = new CompanyService(companyRepository, companyMapperMock, faker);
        CompanyDto companyDto = new CompanyDto(null, "New Company", "New Location");

        Company company = companyMapper.toEntity(companyDto);
        Mockito.when(companyMapperMock.toEntity(companyDto)).thenReturn(company);
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        Mockito.when(companyMapperMock.toDto(company)).thenReturn(companyDto);

        CompanyDto createdDto = service.createCompany(companyDto);
        assertEquals(companyDto, createdDto);
    }

    @Test
    public void testUpdateCompany_Success() {
        CompanyService service = new CompanyService(companyRepository, companyMapperMock, faker);
        Long id = 1L;
        Company existingCompany = new Company(id, "Old Company", "Old Location");
        CompanyDto updatedDto = new CompanyDto(id, "Updated Company", "Updated Location");

        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));
        Mockito.when(companyRepository.save(existingCompany)).thenReturn(existingCompany);
        Mockito.when(companyMapper.toDto(existingCompany)).thenReturn(updatedDto);

        CompanyDto actualDto = service.updateCompany(id, updatedDto);

        assertEquals(updatedDto, actualDto);
    }

    @Test
    public void testUpdateCompany_Failure() {
        CompanyService service = new CompanyService(companyRepository, companyMapper, faker);
        Long id = 1L;
        CompanyDto companyDto = new CompanyDto(id, "Updated Company", "Updated Location");

        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrowsExactly(RuntimeException.class, () -> service.updateCompany(id, companyDto));
    }

    @Test
    public void testDeleteCompany_Success() {
        CompanyService service = new CompanyService(companyRepository, companyMapper, faker);
        Long id = 1L;
        Company company = new Company(id, "Company to Delete", "Location");

        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        service.deleteCompany(id);
    }
}