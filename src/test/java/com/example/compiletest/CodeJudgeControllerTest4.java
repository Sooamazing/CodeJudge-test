// package com.example.compiletest;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;
//
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.model.S3Object;
//
// @WebMvcTest(CodeJudgeController.class)
// public class CodeJudgeControllerTest4 {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private AmazonS3 amazonS3; // AmazonS3 인터페이스를 모킹
//
// 	@Test
// 	public void testJudgeCpp() throws Exception {
// 		String testCppFilePath = "src/test/resources/test-cpp-file.cpp";
// 		String testAnswerFilePath = "src/test/resources/test-cpp-answer.txt";
// 		String expectedOutput = "Hello, World!\n";
//
// 		// AmazonS3 모킹 설정
// 		S3Object mockS3Object = new S3Object();
// 		when(amazonS3.getObject("your-bucket", "your-cpp-file.cpp")).thenReturn(mockS3Object);
//
// 		mockMvc.perform(get("/cpp")
// 				.param("s3Url", testCppFilePath)
// 				.param("answerUrl", testAnswerFilePath))
// 			.andExpect(status().isOk())
// 			.andExpect(content().string(expectedOutput));
// 	}
// }
