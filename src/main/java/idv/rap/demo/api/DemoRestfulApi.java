package idv.rap.demo.api;

import idv.rap.demo.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * @author raphael.wong
 * @since 03 June 2022
 */
@Slf4j
@RestController
public class DemoRestfulApi {

    public final StorageService storageService;

    public DemoRestfulApi(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostConstruct
    public void init() {
        storageService.deleteAll();
        storageService.init();
    }

    @GetMapping(value = "/info/{filename}")
    public void getInfo(@PathVariable("filename") String filename) {
        log.info("get info request. filename=[{}]", filename);
        // Resource resource = new ClassPathResource("application.properties");
        Resource resource;
        resource = new ClassPathResource(filename);
        log.info("resource: exists=[{}]", resource.exists());
        log.info("resource: filename=[{}]", resource.getFilename());
        try {
            log.info("resource: content_length=[{}]", resource.contentLength());
            log.info("resource: file=[{}]", resource.getFile());
            log.info("resource: isFile=[{}]", resource.isFile());
            log.info("resource: description=[{}]", resource.getDescription());
            log.info("resource: uri=[{}]", resource.getURI());
            log.info("resource: url=[{}]", resource.getURL());
        } catch (IOException e) {
            log.info("get info:io exception, ", e);
        }
    }

    @DeleteMapping(value = "/{filename}")
    public void deleteFile(@PathVariable("filename") String filename) {
        log.info("delete file request: filename=[{}]", filename);
        String filepath = "/Users/raphael/Documents/repository_git/demo/log/demo/" + filename;
        File file = new File(filepath);
        if (file.exists()) {
            log.info("file found. file=[{}]", file.getAbsolutePath());
            if (file.delete()) {
                log.info("file deleted.");
            } else {
                log.info("file delete fail. filename=[{}]", filename);
            }
        } else {
            log.info("file not found. filepath=[{}]", filepath);
        }
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}
