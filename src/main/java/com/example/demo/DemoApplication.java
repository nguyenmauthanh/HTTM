package com.example.demo;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages={"com.example.demo"})
public class DemoApplication {

	public static void main(String[] args) {
		OpenCV.loadShared();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Imgcodecs imageCodecs = new Imgcodecs();
		SpringApplication.run(DemoApplication.class, args);
	}

}
