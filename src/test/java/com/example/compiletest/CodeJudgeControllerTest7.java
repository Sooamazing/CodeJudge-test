// package com.example.compiletest;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import java.io.IOException;
//
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
// @WebMvcTest(CodeJudgeController.class)
// public class CodeJudgeControllerTest7 {
//
// 	@InjectMocks
// 	private CodeJudgeController codeJudgeController;
//
// 	private MockMvc mockMvc;
//
// 	@BeforeEach
// 	public void setup() throws IOException {
// 		this.mockMvc = MockMvcBuilders.standaloneSetup(codeJudgeController).build();
// 	}
//
// 	@Test
// 	public void testJudgePython() throws Exception {
// 		// 테스트용 Python 파일 경로
// 		String testPythonFilePath = "src/test/resources/test-python-file.py";
//
// 		// 테스트용 정답 파일 경로
// 		String testAnswerFilePath = "src/test/resources/test-python-answer.txt";
//
// 		// 예상 출력값 (Hello, World!)
// 		String expectedOutput = "Hello, World!\n";
//
// 		mockMvc.perform(get("/python")
// 				.param("s3Url", testPythonFilePath)  // 로컬 파일 경로를 사용
// 				.param("answerUrl", testAnswerFilePath))  // 로컬 파일 경로를 사용
// 			.andExpect(status().isOk())
// 			.andExpect(content().string(expectedOutput));
// 	}
// }
