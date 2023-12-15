// package com.example.compiletest;
// import com.github.dockerjava.api.DockerClient;
// import com.github.dockerjava.api.command.CreateContainerResponse;
// import com.github.dockerjava.api.model.Bind;
// import com.github.dockerjava.api.model.PortBinding;
// import com.github.dockerjava.core.DockerClientBuilder;
// import com.github.dockerjava.core.DockerClientConfig;
// import com.github.dockerjava.core
// .DefaultDockerClientConfig;
//
// public class CompileTest {
// 	public static void main(String[] args) {
//
// 		DockerClient dockerClient
// 			= DockerClientBuilder.getInstance("tcp://docker.baeldung.com:2375").build();
//
// 		CreateContainerResponse container
// 			= dockerClient.createContainerCmd("mongo:3.6")
// 			.withCmd("--bind_ip_all")
// 			.withName("mongo")
// 			.withHostName("baeldung")
// 			.withEnv("MONGO_LATEST_VERSION=3.6")
// 			.withPortBindings(PortBinding.parse("9999:27017"))
// 			.withBinds(Bind.parse("/Users/baeldung/mongo/data/db:/data/db")).exec();
//
// 		dockerClient.startContainerCmd(container.getId()).exec();
//
// 		dockerClient.stopContainerCmd(container.getId()).exec();
//
// 		dockerClient.killContainerCmd(container.getId()).exec();
//
// 	}
// }
