// package com.example.compiletest;
//
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import lombok.RequiredArgsConstructor;
// import software.amazon.awssdk.core.ResponseInputStream;
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.model.GetObjectRequest;
// import software.amazon.awssdk.services.s3.model.S3Exception;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.URI;
// import java.net.URISyntaxException;
//
// @RestController
// @RequiredArgsConstructor
// public class CodeJudgeController {
//
// 	private final S3Client s3Client;
//
// 	// public CodeJudgeController() {
// 	// 	// AWS SDK v2 S3 클라이언트 생성
// 	// 	this.s3Client = S3Client.builder()
// 	// 		.region(Region.AP_NORTHEAST_2) // 원하는 리전 선택
// 	// 		.build();
// 	// }
//
// 	@GetMapping("/cpp")
// 	public boolean judgeCpp(
// 		@RequestParam String s3Url,
// 		@RequestParam String answerUrl
// 	) {
// 		return judgeCode(s3Url, answerUrl, "cpp");
// 	}
//
// 	@GetMapping("/java")
// 	public boolean judgeJava(
// 		@RequestParam String s3Url,
// 		@RequestParam String answerUrl
// 	) {
// 		return judgeCode(s3Url, answerUrl, "java");
// 	}
//
// 	@GetMapping("/python")
// 	public boolean judgePython(
// 		@RequestParam String s3Url,
// 		@RequestParam String answerUrl
// 	) {
// 		return judgeCode(s3Url, answerUrl, "py");
// 	}
//
// 	private boolean judgeCode(String s3Url, String answerUrl, String extension) {
// 		try {
// 			// S3에서 파일 다운로드
// 			String downloadedContent = downloadFileFromS3(s3Url);
//
// 			// 소스 코드 실행
// 			String output = executeSourceCode(downloadedContent, extension);
//
// 			// 정답 파일 읽기
// 			String answerContent = downloadFileFromS3(answerUrl);
//
// 			// 출력 결과와 정답 비교
// 			return output.trim().equals(answerContent.trim());
// 		} catch (IOException | InterruptedException e) {
// 			// 예외 처리: 파일 다운로드, 실행, URI 파싱 중 발생한 예외 처리
// 			System.err.println("Error occurred: " + e.getMessage());
// 			return false;
// 		}
// 	}
//
// 	private String downloadFileFromS3(String s3Url) throws IOException {
// 		try {
// 			// S3 객체 다운로드
// 			URI uri = new URI(s3Url);
// 			String bucketName = uri.getHost();
// 			String objectKey = uri.getPath().substring(1);
//
// 			GetObjectRequest getObjectRequest = GetObjectRequest.builder()
// 				.bucket(bucketName)
// 				.key(objectKey)
// 				.build();
//
// 			ResponseInputStream<?> objectStream = s3Client.getObject(getObjectRequest);
//
// 			// S3에서 읽어온 객체 스트림을 문자열로 변환
// 			BufferedReader reader = new BufferedReader(new InputStreamReader(objectStream));
// 			StringBuilder content = new StringBuilder();
// 			String line;
// 			while ((line = reader.readLine()) != null) {
// 				content.append(line).append("\n");
// 			}
//
// 			return content.toString();
// 		} catch (S3Exception | URISyntaxException e) {
// 			// 예외 처리: S3에서 파일 다운로드 중 발생한 예외 처리
// 			e.printStackTrace();
// 			throw new IOException("Failed to download file from S3.");
// 		}
// 	}
//
// 	private String executeSourceCode(String sourceCode, String extension) throws IOException, InterruptedException {
// 		if (extension.equals("cpp")) {
// 			// C++ 소스 코드 실행
// 			ProcessBuilder processBuilder = new ProcessBuilder("g++", "-o", "cpp_code");
// 			processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
// 			processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
// 			Process process = processBuilder.start();
// 			process.waitFor();
//
// 			processBuilder = new ProcessBuilder("./cpp_code");
// 			process = processBuilder.start();
// 			process.waitFor();
//
// 			// 실행 결과 반환
// 			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
// 			StringBuilder output = new StringBuilder();
// 			String line;
// 			while ((line = reader.readLine()) != null) {
// 				output.append(line).append("\n");
// 			}
// 			return output.toString();
// 		} else if (extension.equals("java")) {
// 			// Java 소스 코드 실행
// 			String className = "Main"; // 클래스 이름 (파일명에 따라 변경)
// 			String javaFilePath = "/path/to/java/file/Main.java"; // Java 파일 경로 설정
//
// 			// 컴파일
// 			ProcessBuilder compileBuilder = new ProcessBuilder("javac", javaFilePath);
// 			compileBuilder.redirectErrorStream(true);
// 			Process compileProcess = compileBuilder.start();
// 			compileProcess.waitFor();
//
// 			// 실행
// 			ProcessBuilder runBuilder = new ProcessBuilder("java", className);
// 			runBuilder.redirectErrorStream(true);
// 			Process runProcess = runBuilder.start();
// 			runProcess.waitFor();
//
// 			// 실행 결과 반환
// 			BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
// 			StringBuilder output = new StringBuilder();
// 			String line;
// 			while ((line = reader.readLine()) != null) {
// 				output.append(line).append("\n");
// 			}
// 			return output.toString();
// 		} else if (extension.equals("py")) {
// 			// Python 소스 코드 실행
// 			ProcessBuilder processBuilder = new ProcessBuilder("python3", "-c", sourceCode);
// 			processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
// 			processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
// 			Process process = processBuilder.start();
// 			process.waitFor();
//
// 			// 실행 결과 반환
// 			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
// 			StringBuilder output = new StringBuilder();
// 			String line;
// 			while ((line = reader.readLine()) != null) {
// 				output.append(line).append("\n");
// 			}
// 			return output.toString();
// 		}
//
// 		return ""; // 언어에 맞는 실행 코드가 없을 경우 빈 문자열 반환
// 	}
// }
