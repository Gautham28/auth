package com.gautham.auth.services;

import com.gautham.auth.models.Note;
import java.util.List;

// An interface just lists out the methods we plan to build
public interface NoteService {
    Note createNoteForUser(String username, String content); // Creates a note
    Note updateNoteForUser(Long noteId, String content, String username); // Updates an existing note
    void deleteNoteForUser(Long noteId, String username); // Deletes a note
    List<Note> getNotesForUser(String username); // Gets all notes for a specific person
}