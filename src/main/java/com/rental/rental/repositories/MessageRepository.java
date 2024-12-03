package com.rental.rental.repositories;

import com.rental.rental.entities.Message;
import com.rental.rental.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}