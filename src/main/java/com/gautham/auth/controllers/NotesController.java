package com.gautham.auth.controllers;

import com.gautham.auth.models.Note;
import com.gautham.auth.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Tells Spring this class handles REST API requests and returns JSON data
@RequestMapping("/api/notes") // Sets the base URL for all methods in this class
public class NotesController {

    @Autowired // Injects our NoteService business logic
    private NoteService noteService;

    // Handles POST requests to /api/notes (Used for creating things)
    @PostMapping
    public Note createNote(@RequestBody String content, // @RequestBody extracts the data sent in the HTTP request body
                           @AuthenticationPrincipal UserDetails userDetails) { // Asks Spring Security to provide the currently logged-in user

        String username = userDetails.getUsername(); // Extract the username from the security token
        System.out.println("USER DETAILS: " + username); // Print to console for debugging
        return noteService.createNoteForUser(username, content); // Pass data to the service layer
    }

    // Handles GET requests to /api/notes (Used for fetching things)
    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername(); // Get the logged-in user
        return noteService.getNotesForUser(username); // Fetch only their notes
    }

    // Handles PUT requests to /api/notes/{noteId} (Used for updating things)
    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable Long noteId, // @PathVariable grabs the {noteId} from the URL path
                           @RequestBody String content, // Grabs the new text from the request body
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return noteService.updateNoteForUser(noteId, content, username);
    }

    // Handles DELETE requests to /api/notes/{noteId} (Used for deleting things)
    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        noteService.deleteNoteForUser(noteId, username); // Passes the ID to the service to be deleted
    }
}