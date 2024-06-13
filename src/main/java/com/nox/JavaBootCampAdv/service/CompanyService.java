package com.nox.JavaBootCampAdv.service;

import com.github.javafaker.Faker;
import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.entity.Company;
import com.nox.JavaBootCampAdv.mapper.CompanyMapper;
import com.nox.JavaBootCampAdv.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final Faker faker;

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .toList();
    }

    public Optional<CompanyDto> getCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(companyMapper::toDto);
    }

    public CompanyDto createCompany(CompanyDto company) {
        Company companyEntity = companyMapper.toEntity(company);
        return companyMapper.toDto(
                companyRepository.save(companyEntity)
        );
    }

    public CompanyDto updateCompany(Long id, CompanyDto company) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setLocation(company.getLocation());
            return companyMapper.toDto(
                    companyRepository.save(companyToUpdate)
            );
        } else {
            throw new RuntimeException("Company not found by id = %s".formatted(id));
        }
    }

    public void deleteCompany(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.delete(companyOptional.get());
        } else {
            throw new RuntimeException("Company not found by id = %s".formatted(id));
        }
    }

    public List<CompanyDto> generateCompanies(int count) {
        return IntStream.range(0, count)
                .mapToObj(_ -> {
                    Company company = new Company();
                    company.setName(faker.company().name());
                    company.setLocation(faker.address().fullAddress());
                    return companyMapper.toDto(companyRepository.save(company));
                })
                .collect(Collectors.toList());
    }

    Company getCompanyByIdOrThrow(Long id) {
        return companyRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found by id = %s".formatted(id)));
    }
}