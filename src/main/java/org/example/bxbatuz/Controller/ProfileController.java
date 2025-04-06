package org.example.bxbatuz.Controller;

import lombok.AllArgsConstructor;
import org.example.bxbatuz.Dto.ProfileResponse;
import org.example.bxbatuz.Response;
import org.example.bxbatuz.Service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getEmployeeProfile(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfile(id));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = profileService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Disposition",
                        "inline;" +
                                " filename=\"qrcode.png\"")
                .body(image);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Response> uploadEmpImage(@RequestParam("id") Long id,
                                                   @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(profileService.uploadImage(id, image));
    }
}
