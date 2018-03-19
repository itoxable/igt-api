package uk.doneby.igt.service;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageServiceImpl implements StorageService {

	
	@Value("${image.folder}")
	private String imageFolder;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String store(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
        }
		
		String fileName = System.currentTimeMillis()+file.getOriginalFilename().toLowerCase();
    	Path target = FileSystems.getDefault().getPath(imageFolder).resolve(fileName);

    	Files.copy(file.getInputStream(), target);
         
    	return fileName;

	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		System.out.println("--------------------------------");
		System.out.println(imageFolder+"/"+filename);
		Path target = FileSystems.getDefault().getPath(imageFolder+"/"+filename);
		return target;
	}

	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
