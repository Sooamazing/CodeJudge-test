package com.example.compiletest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FileUploadController {

	private final AmazonS3 amazonS3;
	private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			String fileUrl = "https://" + bucket + "/test" + fileName;
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/delete")
	public String deleteFile(@RequestBody String fileName) {

		Region region = Region.AP_NORTHEAST_2; // region

		// S3Client s3Client = S3Client.builder().region( region ).build();
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(fileName).build();

		// amazonS3Client에 filePath가 정말 있는지 확인
		if (amazonS3.doesObjectExist(bucket, fileName)) {
			// 있으면 삭제
			amazonS3.deleteObject(bucket, fileName);
			return "File exists";
		} else {
			return "File does not exist";
		}
	}

	@GetMapping("/download")
	public String downloadFile(@RequestBody String fileName) throws Exception {

		S3Object object = amazonS3.getObject(bucket, fileName);

		// url로 변환 /test-python-file.py
		URL url = amazonS3.getUrl(bucket, fileName);

		// S3 url에서
		String s3Url = "s3://webide-test/test-python-file.py";

		try {
			GetObjectRequest objectRequest = GetObjectRequest
				.builder()
				.key(fileName)
				.bucket(bucket)
				.build();

			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();

			// Write the data to a local file.
			String path = "/Users/goorm/Documents/Study/compiletest/src/test/resources/s3-test-file.txt";
			File myFile = new File(path);
			OutputStream os = new FileOutputStream(myFile);
			os.write(data);
			System.out.println("Successfully obtained bytes from an S3 object");
			os.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}

		// url로 파일 경로 지정
		File file = new File(url.getPath());

		// python 파일 실행
		ResponseDto responseDto = helloWorldPy(file);

		return ResponseEntity.ok(responseDto.toString()).toString();

	}

	public ResponseDto helloWorldPy(File file) {
		String pythonCode = "print('hello python')";
		String filePath = "example.py"; // 파일 경로와 이름 지정

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(pythonCode);
			System.out.println("Python file created successfully.");
		} catch (IOException e) {
			System.err.println("An error occurred.");
		}
		StringBuilder answer = getStringBuilder("python3", filePath);
		return new ResponseDto(answer.toString());
	}

	private static StringBuilder getStringBuilder(String... command) {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		StringBuilder answer = new StringBuilder();
		try {
			Process process = processBuilder.start();
			InputStream inputStream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				// 실행 결과 처리
				log.info("line output={}", line);
				answer.append(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			answer.delete(0, answer.length());
			answer.append("error");
		}
		return answer;
	}

}
