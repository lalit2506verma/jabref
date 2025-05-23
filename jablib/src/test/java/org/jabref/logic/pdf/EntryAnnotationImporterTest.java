package org.jabref.logic.pdf;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.jabref.logic.FilePreferences;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.pdf.FileAnnotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EntryAnnotationImporterTest {

    private final BibDatabaseContext databaseContext = mock(BibDatabaseContext.class);
    private BibEntry entry;

    @BeforeEach
    void setUp() {
        entry = new BibEntry();
        when(databaseContext.getFileDirectories(any())).thenReturn(List.of(Path.of("src/test/resources/pdfs/")));
    }

    @Test
    void readEntryExampleThesis() {
        // given
        entry.setField(StandardField.FILE, ":thesis-example.pdf:PDF");
        EntryAnnotationImporter entryAnnotationImporter = new EntryAnnotationImporter(entry);

        // when
        Map<Path, List<FileAnnotation>> annotations = entryAnnotationImporter.importAnnotationsFromFiles(databaseContext, mock(FilePreferences.class));

        // then
        int fileCounter = 0;
        int annotationCounter = 0;
        for (List<FileAnnotation> annotationsOfFile : annotations.values()) {
            fileCounter++;
            annotationCounter += annotationsOfFile.size();
        }
        assertEquals(1, fileCounter);
        assertEquals(2, annotationCounter);
    }
}
