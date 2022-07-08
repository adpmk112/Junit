package com.exercise;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.BookRepository;
import com.exercise.dao.BookService;
import com.exercise.model.BookBean;


@SpringBootTest
@AutoConfigureMockMvc
public class TestBookController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	BookService bookService;
	
	
	@MockBean
	BookRepository repo;
	
	//testing displayView method
	@Test
	public void testdisplayView() throws Exception {
		this.mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andExpect(model().attributeExists("list"));
	}
	
	//testing setupaddbook method
	@Test
	public void testsetupaddbook() throws Exception {
		this.mockMvc.perform(get("/setupaddbook"))
		.andExpect(status().isOk())
		.andExpect(view().name("addbook"))
		.andExpect(model().attributeExists("bookBean"));
	}
	
	//testing addbook method when validated fail
	@Test
	public void testaddbookvalidate() throws Exception {
		this.mockMvc.perform(post("/addbook"))
		.andExpect(status().isOk())
		.andExpect(view().name("addbook"));	
	}
	
	//testing addbook method when validated pass
	@Test
	public void testaddbookok() throws Exception {
		BookBean bookBean=new BookBean();
		bookBean.setBookCode("BK-001");
		bookBean.setBookTitle("Java");
		bookBean.setBookAuthor("James");
		bookBean.setBookPrice("2000");
		this.mockMvc.perform(post("/addbook").flashAttr("bookBean", bookBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));	
	}
	
	//testing deletebook
		@Test
		public void testdeletebook() throws Exception {
			this.mockMvc.perform(get("/deleteBook").param("code","BK-001"))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/"));	
		}
		
	
	//testing updatebook method when validated fail
	@Test
	public void testupdatebookvalidate() throws Exception {
		this.mockMvc.perform(post("/updatebook"))
		.andExpect(status().isOk())
		.andExpect(view().name("updatebook"));	
	}
	
	//testing updatebook method when validated pass
	@Test
	public void testupdatebookok() throws Exception {
		BookBean bookBean=new BookBean();
		bookBean.setBookCode("BK-001");
		bookBean.setBookTitle("Java");
		bookBean.setBookAuthor("James");
		bookBean.setBookPrice("2000");
		this.mockMvc.perform(post("/updatebook").flashAttr("bookBean", bookBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));	
	}
	
	
	
	
}
