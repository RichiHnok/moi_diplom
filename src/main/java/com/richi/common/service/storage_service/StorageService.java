package com.richi.common.service.storage_service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();
    
    Path store(MultipartFile file);

	Path storeInFolder(MultipartFile file, Path folderPath) throws Exception;

    Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteFile(Path filePath) throws IOException;

	void deleteAll();

	public Path getRootLocation();
}
