// package com.example.compiletest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
//
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.services.s3.model.S3Object;
//
// @WebMvcTest(CodeJudgeController.class)
// public class CodeJudgeControllerTest3 {
//
// 	@InjectMocks
// 	private CodeJudgeController codeJudgeController;
//
// 	@Mock
// 	private AmazonS3 mockS3Client;
//
// 	@MockBean
// 	private AmazonS3ClientBuilder s3ClientBuilder;
//
// 	private MockMvc mockMvc;
//
// 	@BeforeEach
// 	public void setup() throws IOException {
// 		this.mockMvc = MockMvcBuilders.standaloneSetup(codeJudgeController).build();
//
// 		// S3 클라이언트 모의 설정
// 		File testFile = new File("src/test/resources/test-cpp-file.cpp"); // 테스트 파일 경로
// 		InputStream inputStream = new FileInputStream(testFile);
// 		S3Object mockS3Object = new S3Object();
// 		mockS3Object.setObjectContent(inputStream);
// 		when(mockS3Client.getObject("your-bucket", "your-cpp-file.cpp")).thenReturn(mockS3Object);
// 	}
//
// 	@Test
// 	public void testJudgeCpp() throws Exception {
// 		// 테스트용 C++ 파일 경로
// 		String testCppFilePath = "src/test/resources/test-cpp-file.cpp";
//
// 		// 테스트용 정답 파일 경로
// 		String testAnswerFilePath = "src/test/resources/test-cpp-answer.txt";
//
// 		// 예상 출력값 (Hello, World!)
// 		String expectedOutput = "Hello, World!\n";
//
// 		mockMvc.perform(get("/cpp")
// 				.param("s3Url", testCppFilePath)  // 로컬 파일 경로를 사용
// 				.param("answerUrl", testAnswerFilePath))  // 로컬 파일 경로를 사용
// 			.andExpect(status().isOk())
// 			.andExpect(content().string(expectedOutput));
// 	}
// }
