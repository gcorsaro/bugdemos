package com.sample.bug.bugsecuritydemo.controller.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.sample.bug.bugsecuritydemo.controller.TestBaseController;
import com.sample.bug.bugsecuritydemo.security.WithUser;

public class DemoRestTest extends TestBaseController {

    @Test
    @WithUser(username = "Admin")
    public void whenFindListAdmin_thenReturnALL() {
    	try {
    		System.out.println(this.mockMvc.perform(get("/secure/demo/test1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(jsonPath("$", hasSize(4)))
    		.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    @WithUser(username = "Other")
    public void whenFindListOther_thenReturnNothing() {
    	try {
    		System.out.println(this.mockMvc.perform(get("/secure/demo/test2")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(jsonPath("$", hasSize(0)))
    		.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
