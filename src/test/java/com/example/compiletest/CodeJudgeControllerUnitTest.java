package com.example.compiletest;

import com.example.compiletest.CodeJudgeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CodeJudgeController.class)
public class CodeJudgeControllerUnitTest {

	@InjectMocks
	private CodeJudgeController codeJudgeController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(codeJudgeController).build();
	}

	@Test
	public void testJudgeCpp() throws Exception {
		// 테스트용 C++ 파일 읽기
		String testCppFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-cpp-file.cpp";
		String cppCode = new String(Files.readAllBytes(Paths.get(testCppFilePath)), StandardCharsets.UTF_8);

		// 테스트용 정답 파일 읽기
		String testAnswerFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-cpp-answer.txt";
		String expectedOutput = new String(Files.readAllBytes(Paths.get(testAnswerFilePath)), StandardCharsets.UTF_8);

		mockMvc.perform(get("/cpp")
				.param("s3Url", "file://" + testCppFilePath)  // 로컬 파일 경로를 사용
				.param("answerUrl", "file://" + testAnswerFilePath))  // 로컬 파일 경로를 사용
			.andExpect(status().isOk())
			.andExpect(content().string(expectedOutput));
	}

	// 여기에 테스트Java, 테스트Python에 대한 테스트 코드 추가
}
