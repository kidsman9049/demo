package idv.rap.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author raphael.wong
 * @since 09 July 2022
 */
@Slf4j
@Service
public class FilesStorageServiceImpl implements StorageService {

    private Path root;

    public FilesStorageServiceImpl() {
        root = Paths.get("upload");
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            log.info("store file::io exception, ", e);
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
