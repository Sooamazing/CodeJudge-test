package com.example.compiletest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CodeJudgePy {
	public static void main(String[] args) {
		String pythonFilePath = "/Users/goorm/Documents/Study/compiletest/src/test/resources/test-python-file.py";
		runPythonScript(pythonFilePath);
	}

	public static void runPythonScript(String pythonFilePath) {
		File scriptFile = new File(pythonFilePath);
		File outputFile = new File("output.txt"); // 출력 파일

		if (!scriptFile.exists()) {
			System.err.println("Python script file not found: " + pythonFilePath);
			return;
		}

		ProcessBuilder runBuilder = new ProcessBuilder("python3", pythonFilePath);
		runBuilder.redirectErrorStream(true); // 표준 에러를 표준 출력으로 리디렉션

		// 이건 뭐지?
		File inputFile = new File("test/resources/input-python-script.txt"); // 입력 파일
		runBuilder.redirectInput(inputFile);
		runBuilder.redirectOutput(outputFile);

		try {
			Process process = runBuilder.start();
			String output = readOutput(process.getInputStream());
			int exitCode = process.waitFor();

			// 출력 결과와 에러 코드 로깅
			System.out.println("Output:\n" + output);
			System.out.println("Exit code: " + exitCode);
		} catch (IOException e) {
			System.err.println("Error executing Python script: " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("Execution interrupted: " + e.getMessage());
		}
	}

	private static String readOutput(InputStream inputStream) throws IOException {
		StringBuilder output = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
		}
		return output.toString();
	}
}
