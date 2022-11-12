package com.example.demo.controller;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.model.Finger;
import com.example.demo.model.Student;
import com.example.demo.repository.FingerRepository;
import com.example.demo.repository.StudentRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/httm")
public class FingerprintController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FingerprintController.class);
    public static final Float THREADOLD = Float.valueOf(44);

    public static final String PATH_FILE_DOWNLOAD = "C:\\Users\\Bong\\OneDrive\\Desktop\\CODE\\BONG\\PTHTTM\\demo\\demo\\Files-Upload\\";

    public static final String PATH_FILE_IMAGES = "C:\\Users\\Bong\\OneDrive\\Desktop\\CODE\\BONG\\PTHTTM\\demo\\demo";
    public static final double RATIO = 0.5;

    private final StudentRepository studentRepository;

    private final FingerRepository fingerRepository;

    @Autowired
    public FingerprintController(StudentRepository studentRepository, FingerRepository fingerRepository) {
        this.studentRepository = studentRepository;
        this.fingerRepository = fingerRepository;
    }

    @PostMapping(value = "/finger", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> checkFinger(@RequestParam(value = "file") MultipartFile file) throws IOException {
        LOGGER.info("{}", file.getName());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileCode = saveFile(fileName, file);

        Resource resource = getFileAsResource(fileCode);

        List<Finger> fingers = (List<Finger>) fingerRepository.findAll();

        for(Finger finger : fingers){
            boolean check = compareImages( PATH_FILE_DOWNLOAD + resource.getFilename() , PATH_FILE_IMAGES + finger.getFilePath() + "\\" + finger.getName());
            if(check){
                LOGGER.info(PATH_FILE_DOWNLOAD + resource.getFilename());
                LOGGER.info(PATH_FILE_IMAGES + finger.getFilePath() + "\\" + finger.getName());
                Student student = studentRepository.findById(Integer.valueOf(finger.getStudentCode()));
                return new ResponseEntity<>(new ResponseMessage(student.getStudentCode(), student.getName() , true), HttpStatus.OK);
            }
        }
//        List<Student> students = (List<Student>) studentRepository.findAll();
//        for(Student student : students){
//            List<Finger> fingers = (List<Finger>) fingerRepository.findByidStudent(student.getId());
//            for(Finger finger : fingers){
//                boolean check = compareImages( PATH_FILE_DOWNLOAD + resource.getFilename() , PATH_FILE_IMAGES + finger.getFilePath() + "\\" + finger.getName());
//                if(check == true){
//                    return new ResponseEntity<>(new ResponseMessage(student.getStudentCode(), student.getName() , true), HttpStatus.OK);
//                }
//            }
//        }

        return new ResponseEntity<>(new ResponseMessage("Khong ton tai","Khong ton tai" , false), HttpStatus.OK);
    }
    private Path foundFile = null;
    public Resource getFileAsResource(String fileCode) throws IOException {

        Path dirPath = Paths.get("Files-Upload");

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

    private boolean compareImages(String path1, String path2){
        System.out.println(path1 + "-" + path2);

        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        // Anh 1
        Mat img1 = Imgcodecs.imread(path1, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors1 = new Mat();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();

        featureDetector.detect(img1, keypoints1);
        descriptor.compute(img1, keypoints1, descriptors1);

        // Anh 2
        Mat img2 = Imgcodecs.imread(path2, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        featureDetector.detect(img2, keypoints2);
        descriptor.compute(img2, keypoints2, descriptors2);

        // So sanh 2 bo diem anh
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1, descriptors2, matches);

        List<Float> distance = new ArrayList<>();
        for (DMatch m : matches.toArray()) {
            if(m.distance < THREADOLD){
                distance.add(m.distance);
            }
        }

        if((float)distance.size() / keypoints1.total() * 100 > RATIO){
            return true;
        }else {
            return false;
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity("Nguyen Dinh Huy", HttpStatus.OK);
    }

    public String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get("Files-Upload");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return fileCode;
    }
}
