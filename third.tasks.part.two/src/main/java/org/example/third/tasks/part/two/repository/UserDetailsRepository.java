package org.example.third.tasks.part.two.repository;

import org.example.third.tasks.part.two.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<User, String> {

}
