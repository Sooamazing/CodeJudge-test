package com.example.compiletest;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

@Slf4j
public class CodeJudgeController3 {

	public static void main(String[] args) {
		String cppFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-cpp-file.cpp";
		compileAndRunCpp(cppFilePath);
	}

	public static void compileAndRunCpp(String cppFilePath) {
		try {
			String outputExecutable = "test-cpp-executable"; // 실행 파일 이름 설정

			// C++ 파일 컴파일
			ProcessBuilder compileBuilder = new ProcessBuilder("g++", "-o", outputExecutable, cppFilePath);
			// cppFilePath 경로 못 찾을 때도 작동, But output.txt 내용은 안 변함. -> 못 찾으면 안 되도록 처리해야 함. 근데 왜 되지?
			compileBuilder.redirectErrorStream(true);
			Process compileProcess = compileBuilder.start();
			compileProcess.waitFor();

			// 컴파일된 프로그램 실행
			ProcessBuilder runBuilder = new ProcessBuilder("./" + outputExecutable);
			File inputFile = new File("/Users/goorm/Documents/Study/compiletest/src/test/resources/input-cpp-src.txt"); // 입력 파일
			File outputFile = new File("/Users/goorm/Documents/Study/compiletest/src/test/resources/output.txt"); // 출력 파일

			runBuilder.redirectInput(inputFile);
			runBuilder.redirectOutput(outputFile);
			Process runProcess = runBuilder.start();
			runProcess.waitFor();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public DeleteObjectResponse delete(String filePath, String bucketName){

		Region region = Region.AP_NORTHEAST_2; // region

		S3Client s3Client = S3Client.builder().region( region ).build();

		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket( bucketName ).key( filePath ).build();

		return s3Client.deleteObject(deleteObjectRequest);
	}
}
