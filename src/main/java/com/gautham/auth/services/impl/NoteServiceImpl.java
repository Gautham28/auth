package com.gautham.auth.services.impl;

import com.gautham.auth.models.Note;
import com.gautham.auth.repositories.NoteRepository;
import com.gautham.auth.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Tells Spring to treat this class as a Service component and manage it automatically
public class NoteServiceImpl implements NoteService {

    @Autowired // Tells Spring to automatically inject the NoteRepository we created earlier
    private NoteRepository noteRepository;

    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note(); // Create a new empty Note object
        note.setContent(content); // Add the text content
        note.setOwnerUsername(username); // Attach it to the specific user
        return noteRepository.save(note); // Save it to MySQL and return the saved result
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        // First, search the database for the note by its ID.
        // If it doesn't exist, throw an error to stop the process.
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setContent(content); // Update the text content of the found note
        return noteRepository.save(note); // Save the updated note back to MySQL
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        noteRepository.deleteById(noteId); // Delete the note from MySQL using its ID
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        // Use our custom repository method to fetch only this specific user's notes
        return noteRepository.findByOwnerUsername(username);
    }
}
