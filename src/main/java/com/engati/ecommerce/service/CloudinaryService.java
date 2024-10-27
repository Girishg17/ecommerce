package com.engati.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface  CloudinaryService {
    public String upload(MultipartFile file) throws IOException;
}
