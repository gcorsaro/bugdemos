package com.sample.bug.bugsecuritydemo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/secure/demo")
public class DemoController {

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(path = "/test1", produces = APPLICATION_JSON_VALUE)
	public List<String> testList1() {
		return Arrays.asList("VAL1", "VAL2", "VAL3", "VAL4");
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(path = "/test2", produces = APPLICATION_JSON_VALUE)
	@PostFilter("hasAuthority('ADMIN')")
	public List<String> testList2() {
		return Arrays.asList("VAL1", "VAL2", "VAL3", "VAL4");
	}
}
