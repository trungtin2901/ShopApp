package com.cat.shopapp.controllers;

import com.cat.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            //@RequestPart("newFile") MultipartFile newFile,

            BindingResult result
    ){
        try{
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            List<MultipartFile> files = productDTO.getFiles();
            //Kiểm tra nếu ds rỗng
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile newFile: files){
                if(newFile.getSize() == 0){
                    continue;
                }
                //Kiểm tra kích thước file và định dạng
                if(newFile.getSize() > 10 * 1024 * 1024){ // Kích thước file > 10mb
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("file is too large! Maximum file is 10MB");
                }
                //Kiểm tra xem có phải là định dạng file ảnh không
                String contentType = newFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("file must be an image");
                }
                //Lưu file và cập nhật trong thumbnail trong DTO
                String filename = storeFile(newFile);
                //Lưu vào đối tượng product trong DB
            }

            return ResponseEntity.ok("Product create successfully");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //hàm lưu file hình ảnh, xử lý các file bị trùng tên với nhau
    private String storeFile(MultipartFile file) throws IOException{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //Thêm UUID vào trước tên file để đảm bảo rằng tên file là duy nhất
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;
        //Đường dẫn đến thư mục mà bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        //Kiểm tra và tạo thư mục nếu nó chưa tồn tại
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        //sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
    @GetMapping("") //http://localhost:8088/api/v1/products
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page")       int page,// @RequestParam("page") này được lấy từ client và chuyển vào tham số của phương thức ở đây là int page
            @RequestParam("limit")      int limit
    ){
        return ResponseEntity.ok("getAllProduct");
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId){
        return ResponseEntity.ok( "Product with id:" + productId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id){
        return ResponseEntity.ok(String.format("Product with id = %d was delete successfully",id));
    }
}
