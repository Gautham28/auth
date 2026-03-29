package com.gautham.auth.repositories;

import com.gautham.auth.models.Note;
import org.springframework.data.jpa.repository.JpaRepository; // Imports the built-in Spring Data methods
import java.util.List;

// JpaRepository requires two things: <The Entity Type, The Data Type of the Primary Key>
public interface NoteRepository extends JpaRepository<Note, Long> {

    // We create a custom method here. Spring Data JPA is smart enough to see "findByOwnerUsername"
    // and automatically write a SQL query like: SELECT * FROM notes WHERE owner_username = ?
    List<Note> findByOwnerUsername(String ownerUsername);
}
