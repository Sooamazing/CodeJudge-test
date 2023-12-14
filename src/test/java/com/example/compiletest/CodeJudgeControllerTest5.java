// package com.example.compiletest;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Configuration;
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
// // @SpringBootTest(classes = CodeJudgeControllerTest5.Config.class)
// @WebMvcTest(CodeJudgeController.class)
// public class CodeJudgeControllerTest5 {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private AmazonS3 amazonS3;
//
// 	@Test
// 	public void testJudgeCpp() throws Exception {
// 		String testCppFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-cpp-file.cpp";
// 		String testAnswerFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-cpp-answer.txt";
// 		String expectedOutput = "Hello, World!\n";
//
// 		S3Object mockS3Object = new S3Object();
// 		when(amazonS3.getObject("your-bucket", "your-cpp-file.cpp")).thenReturn(mockS3Object);
//
// 		mockMvc.perform(get("/cpp")
// 				.param("s3Url", testCppFilePath)
// 				.param("answerUrl", testAnswerFilePath))
// 			.andExpect(status().isOk())
// 			.andExpect(content().string(expectedOutput));
// 	}
//
// 	@Configuration
// 	static class Config {
// 		// 수동으로 설정 클래스 생성
// 	}
// }
