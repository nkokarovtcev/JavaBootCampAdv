package com.nox.JavaBootCampAdv.controller;

import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping()
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping()
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto company) {
        return ResponseEntity.ok(companyService.createCompany(company));
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto company) {
        return ResponseEntity.ok(companyService.updateCompany(id, company));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("generate")
    public ResponseEntity<List<CompanyDto>> generateCompanies(@RequestParam int count) {
        return ResponseEntity.ok(companyService.generateCompanies(count));
    }
}