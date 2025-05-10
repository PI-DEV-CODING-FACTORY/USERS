package com.example.users.repositories;

import com.example.users.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,String> {

}
