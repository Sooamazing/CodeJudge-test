package com.example.compiletest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebMvcTest(CodeJudgeController.class)
public class CodeJudgeControllerTest6 {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testJudgePython() throws Exception {
		// 테스트용 Python 파일 경로
		String testPythonFilePath = "src/test/resources/test-python-file.py";

		// 테스트용 정답 파일 경로
		String testAnswerFilePath = "src/test/resources/test-python-answer.txt";

		// 예상 출력값 (Hello, World!)
		String expectedOutput = "Hello, World!\n";

		mockMvc.perform(MockMvcRequestBuilders.get("/python")
				.param("s3Url", "file://" + testPythonFilePath)  // 로컬 파일 경로를 사용
				.param("answerUrl", "file://" + testAnswerFilePath))  // 로컬 파일 경로를 사용
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(expectedOutput));
	}
}
