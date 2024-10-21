package com.engati.ecommerce.service.serviceImplementation;

import com.cloudinary.Cloudinary;
import com.engati.ecommerce.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) throws IOException {
       Map data=cloudinary.uploader().upload(file.getBytes(),Map.of());
       return data.get("url").toString();
    }
}
