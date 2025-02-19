package com.sm.azblb.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sm.azblb.models.Storage;
import com.sm.azblb.services.IAzureStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class AzureStorageControllers {

	private final IAzureStorageService azureStorageService;
	
	@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("path") String path) throws IOException {
        Storage storage = new Storage(path, file.getOriginalFilename(), file.getInputStream());
        String filePath = azureStorageService.write(storage);
        return ResponseEntity.ok("File uploaded to: " + filePath);
    }
	
	@PutMapping("/update")
    public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("path") String path) throws IOException {
        Storage storage = new Storage(path, file.getOriginalFilename(), file.getInputStream());
        String filePath = azureStorageService.update(storage);
        return ResponseEntity.ok("File updated at: " + filePath);
    }
	
	@GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("path") String path,
                                               @RequestParam("fileName") String fileName) {
        Storage storage = new Storage(path, fileName, null);
        byte[] fileData = azureStorageService.read(storage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam("path") String path) {
        Storage storage = new Storage(path, null, null);
        List<String> fileList = azureStorageService.getAllFile(storage);
        return ResponseEntity.ok(fileList);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("path") String path,
                                             @RequestParam("fileName") String fileName) {
        Storage storage = new Storage(path, fileName, null);
        azureStorageService.delete(storage);
        return ResponseEntity.ok("File deleted: " + fileName);
    }
}
