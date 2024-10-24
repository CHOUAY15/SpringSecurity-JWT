package ma.ensa.projet.doctor.api.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final String imageDirectory = "src/main/resources/static/images/";

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Construct the path to the image file
            Path imagePath = Paths.get(imageDirectory).resolve(imageName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            // Check if the resource exists and is readable
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // Determine the content type of the file
            String contentType = Files.probeContentType(imagePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Return the file as a resource with proper headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
