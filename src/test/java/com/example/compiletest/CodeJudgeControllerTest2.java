package com.example.compiletest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CodeJudgeController.class)
public class CodeJudgeControllerTest2 {

	@InjectMocks
	private CodeJudgeController codeJudgeController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(codeJudgeController).build();
	}

	@Test
	public void testJudgeCpp() throws Exception {
		// 테스트용 C++ 파일 경로
		String testCppFilePath = "src/test/resources/test-cpp-file.cpp";

		// 테스트용 정답 파일 경로
		String testAnswerFilePath = "src/test/resources/test-cpp-answer.txt";

		// 예상 출력값 (Hello, World!)
		String expectedOutput = "Hello, World!\n";

		mockMvc.perform(get("/cpp")
				.param("s3Url", testCppFilePath)  // 로컬 파일 경로를 사용
				.param("answerUrl", testAnswerFilePath))  // 로컬 파일 경로를 사용
			.andExpect(status().isOk())
			.andExpect(content().string(expectedOutput));
	}

	// /java와 /python에 대한 테스트도 비슷하게 추가
}
