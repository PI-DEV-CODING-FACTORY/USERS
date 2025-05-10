package com.example.users.services;

import com.example.users.entities.Company;
import com.example.users.enums.Role;
import com.example.users.repositories.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final UsersServices usersServices;
    private final CompanyRepo companyRepo;

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Optional<Company> getCompanieById(String id) {
        return companyRepo.findById(id);
    }

    public Company saveCompany(Company company) {
        return (Company) usersServices.add(company);
    }

    public void deleteCompany(String id) {
        companyRepo.deleteById(id);
    }
}
