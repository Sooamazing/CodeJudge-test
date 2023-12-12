package com.example.compiletest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunPythonScriptTest {

	@Test
	public void testPythonScriptExecution() {

		try {

			// 고민할 내용
			// 성능 최적화? 도커를 사용할 때 발생하는 ?
			// 컴파일하는 커맨드를 직접 날리는데, 소스는 사용자가 전달하는 상황
			// 그게 악성 코드면? -> run time error 5초면 그냥 docker 종료..? 도커 코드 내에서 불편한 상황이 발생할 수 있음.
			// 도커 내에서 컴파일하는 게 비용이 큼.
			// 여러 개 언어로 할 때, 어떤 식으로 ..... 코드 변경을 최소화하면서 할 수 있는가?
			// 채점 서버를 돌릴 때 connection이 오래 사용되는데, 최대 runtime 시간동안 유지될 텐데, db connection이 연결되어 있다면 기본 10개인 상황에서는 모두 처리될 때까지는 서버가 마비될 수 있음. -> 비동기 처리를 고려.
			// 최초 요청할 때는 요청만 하고, 응답 주고, 이후에는 다른 방법으로 응답 로그.. .비동기로 받을 수 있는 방법..?
			// 채점 timeout은 10초는 되어야 할 것. -> 프로그래머스가 그 정도일 것.
			// 비동기를 이용하는 방식! -> 안 되면 서버 많이 띄우기!

			// 테스트 케이스에 인풋 넣는 방식 고려! -> 프로그래머스는 형식이 쉽지만, 백준은 어려울 것. (output을 빼는 작업도 해야 하니까)
			// 오류 처리
			// 서버 시간 등 최적화 진행 필요


			// Python3 스크립트 실행 명령어
			String pythonScript = "python3";
			String scriptPath = "/Users/goorm/Documents/Study/compiletest/src/main/resources/static/example.py";  // example.py 파일의 경로를 지정해야 합니다.

			// ProcessBuilder를 사용하여 Python 스크립트 실행
			ProcessBuilder processBuilder = new ProcessBuilder(pythonScript, scriptPath);
			Process process = processBuilder.start();

			// Python 스크립트의 출력을 읽어오기
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder outputBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				outputBuilder.append(line).append("\n");
			}

			// 프로세스가 완료될 때까지 기다림
			int exitCode = process.waitFor();
			System.out.println("Python Script Execution Completed with Exit Code: " + exitCode);

			// Python 스크립트의 출력과 예상 값 비교
			String expectedOutput = "hi\n";
			String actualOutput = outputBuilder.toString();
			assertEquals(expectedOutput, actualOutput, "Python script output does not match expected output.");

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}