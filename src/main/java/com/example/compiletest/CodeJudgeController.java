package com.example.compiletest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@RestController
public class CodeJudgeController {

	private final AmazonS3 s3Client;

	public CodeJudgeController() {
		this.s3Client = AmazonS3ClientBuilder.standard().build();
	}

	@GetMapping("/cpp")
	public boolean judgeCpp(@RequestParam String s3Url, @RequestParam String answerUrl) {
		return judgeCode(s3Url, answerUrl, "cpp");
	}

	@GetMapping("/java")
	public boolean judgeJava(@RequestParam String s3Url, @RequestParam String answerUrl) {
		return judgeCode(s3Url, answerUrl, "java");
	}

	@GetMapping("/python")
	public boolean judgePython(@RequestParam String s3Url, @RequestParam String answerUrl) {
		return judgeCode(s3Url, answerUrl, "py");
	}

	private boolean judgeCode(String s3Url, String answerUrl, String extension) {
		try {
			// S3에서 파일 다운로드
			String filePath = downloadFileFromS3(s3Url, extension);

			// 소스 코드 실행
			String output = executeSourceCode(filePath, extension);

			// 정답 파일 읽기
			String answer = new String(Files.readAllBytes(Paths.get(new URI(answerUrl).getPath())), StandardCharsets.UTF_8);

			// 출력 결과와 정답 비교
			return output.trim().equals(answer.trim());
		} catch (IOException | InterruptedException | URISyntaxException e) {
			// 예외 처리: 파일 다운로드, 실행, URI 파싱 중 발생한 예외 처리
			System.err.println("Error occurred: " + e.getMessage());
			return false;
		}
	}

	private String downloadFileFromS3(String s3Url, String extension) throws IOException {
		URI uri = URI.create(s3Url);
		String bucketName = uri.getHost();
		String objectKey = uri.getPath().substring(1);

		// S3에서 파일 다운로드
		S3Object s3object = s3Client.getObject(bucketName, objectKey);
		Scanner scanner = new Scanner(s3object.getObjectContent());
		File sourceFile = File.createTempFile("source", "." + extension);
		try (FileOutputStream out = new FileOutputStream(sourceFile)) {
			while (scanner.hasNext()) {
				out.write(scanner.nextLine().getBytes());
			}
		}
		return sourceFile.getAbsolutePath();
	}

	private String executeSourceCode(String filePath, String extension) throws IOException, InterruptedException {
		ProcessBuilder processBuilder;
		if (extension.equals("cpp")) {
			// C++ 소스 코드 컴파일 및 실행
			processBuilder = new ProcessBuilder("g++", filePath, "-o", "cpp_code");
			processBuilder.start().waitFor();
			processBuilder = new ProcessBuilder("./cpp_code");
		} else if (extension.equals("java")) {
			// Java 소스 코드 컴파일 및 실행
			processBuilder = new ProcessBuilder("javac", filePath);
			processBuilder.start().waitFor();
			String className = new File(filePath).getName().replaceAll(".java$", "");
			processBuilder = new ProcessBuilder("java", className);
		} else {
			// Python 소스 코드 실행
			processBuilder = new ProcessBuilder("python3", filePath);
		}

		Process process = processBuilder.start();
		process.waitFor();

		// 실행 결과 반환
		return new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}
}