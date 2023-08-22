//package com.sparta.travelshooting.S3;
//
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//@AllArgsConstructor
//public class S3Controller {
//
//
//    private final S3Upload s3Upload;
//
//    @PostMapping("/api/auth/image")
//    public S3ResponseDto imageUpload(@RequestPart(required = false) MultipartFile multipartFile) {
//
//        if (multipartFile.isEmpty()) {
//            return new S3ResponseDto("파일이 유효하지 않습니다.");
//        }
//        try {
//            return new S3ResponseDto(s3Upload.uploadFiles(multipartFile, "static"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new S3ResponseDto("파일이 유효하지 않습니다.");
//        }
//    }
//}
