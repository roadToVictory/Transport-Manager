package org.example.transport.dictionary.railway.commercialcategory.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transport.dictionary.railway.commercialcategory.service.CommercialCategoryService;
import org.example.transport.integration.railway.plk.commercialcategory.PkpCommercialCategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/commercial-categories")
public class CommercialCategoryController {
    private final CommercialCategoryService commercialCategoryService;

    @GetMapping("/preview")
    public List<PkpCommercialCategoryResponse.PkpCommercialCategory> getCommercialCategories() {
        return commercialCategoryService.retrieveCommercialCategories();
    }

    @PostMapping("/synchronization")
    public ResponseEntity<Void> synchronizeCommercialCategories() {
        commercialCategoryService.synchronizeCommercialCategories();
        return ResponseEntity.noContent().build();
    }

}
