package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


@RestController
public class TestController {

	HashMap<String, TestData> testDataList = new  HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hello, %s! You sent a get request with a parameter!", message);
	}
	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		logger.info("Recieved message: {}", message);
		return String.format("Hello, %s! You sent a post request with a parameter!", message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody TestData testData) {
		System.out.println("added " + testData.getMessage() + " to list");
		testDataList.put(testData.getMessage(), testData);
		return String.format("Hello, %s! You sent a post request with a requestbody!", testData.getMessage());
	}
	
	@DeleteMapping("/deleteTest/{message")
	public @ResponseBody HashMap<String, TestData> deleteTest(@PathVariable String message) {
		testDataList.remove(message);
		return testDataList;
	}

	@PutMapping("/putTest/{firstName}")
	public @ResponseBody TestData putTest(@PathVariable String message, @RequestBody TestData t) {
		testDataList.replace(message, t);
		return testDataList.get(message);
	}
}
