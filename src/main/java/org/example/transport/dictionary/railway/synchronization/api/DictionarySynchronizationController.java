package org.example.transport.dictionary.railway.synchronization.api;

import lombok.RequiredArgsConstructor;
import org.example.transport.dictionary.railway.synchronization.service.DictionarySynchronizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/dictionaries")
public class DictionarySynchronizationController {
    private final DictionarySynchronizationService dictionarySynchronizationService;

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeAllDictionaries() {
        dictionarySynchronizationService.synchronizeAllDictionaries();
        return ResponseEntity.noContent().build();
    }
}
