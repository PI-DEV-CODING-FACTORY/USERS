package com.example.users.repositories;

import com.example.users.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, String> {

}
