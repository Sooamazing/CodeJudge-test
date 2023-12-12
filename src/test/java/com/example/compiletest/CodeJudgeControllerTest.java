package com.example.compiletest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

@WebMvcTest(CodeJudgeController.class)
public class CodeJudgeControllerTest {

	@Mock
	private AmazonS3 mockS3Client;

	@InjectMocks
	private CodeJudgeController codeJudgeController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(codeJudgeController).build();

		// S3 클라이언트 모의 설정
		File testFile = new File("src/test/resources/test-cpp-file.cpp"); // 테스트 파일 경로
		InputStream inputStream = new FileInputStream(testFile);
		S3Object mockS3Object = new S3Object();
		mockS3Object.setObjectContent(inputStream);
		when(mockS3Client.getObject("your-bucket", "your-cpp-file.cpp")).thenReturn(mockS3Object);
	}

	@Test
	public void testJudgeCpp() throws Exception {
		mockMvc.perform(get("/cpp")
				.param("s3Url", "s3://your-bucket/your-cpp-file.cpp")
				.param("answerUrl", "http://example.com/answer"))
			.andExpect(status().isOk());
	}

	// /java와 /python에 대한 테스트도 비슷하게 추가
}
