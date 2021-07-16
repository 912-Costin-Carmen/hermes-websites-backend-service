package ro.societateahermes.backendservice.controller.controllerImplementation;

import java.io.IOException;
import java.util.List;


import exceptions.ImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.societateahermes.backendservice.controller.ImageControllerInterface;
import ro.societateahermes.backendservice.entities.Image;
import ro.societateahermes.backendservice.service.serviceImplementation.ImageServiceImplementation;


@RestController
@RequestMapping("/files")
public class ImageControllerImplementation implements ImageControllerInterface {

    private final ImageServiceImplementation imageService;

    @Autowired
    public ImageControllerImplementation(ImageServiceImplementation imageService) {
        this.imageService = imageService;
    }

    @Override
    @PostMapping("/cd")
    public void savePhoto(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        imageService.convertMultiPartToFile(multipartFile);
    }

    @Override
    @GetMapping("/all")
    public List<Image> getAll() {
        return imageService.getAll();
    }

    @Override
    @GetMapping("/path")
    public ResponseEntity<?> getImageByPath(@RequestParam("path") String canonicalImagePath) {
        try {
            return new ResponseEntity<Image>(imageService.getImageByPath(canonicalImagePath), HttpStatus.OK);

        } catch (ImageException imageException) {
            return new ResponseEntity<String>(imageException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
