package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.example.springboot.repositories.MyDataRepository;
import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class HelloController {

  @Autowired
  MyDataRepository repository;
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index(
      @ModelAttribute("formModel") MyData mydata,
      ModelAndView mav) {
    mav.setViewName("index");
    mav.addObject("msg", "this is sample content.");
    mav.addObject("formModel", mydata);
    Iterable<MyData> list = repository.findAll();
    mav.addObject("datalist", list);
    return mav;
  }
  
  @RequestMapping(value = "/", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView form(
      @ModelAttribute("formModel") @Validated MyData mydata,
      BindingResult result,
	  ModelAndView mav) {
	ModelAndView res = null;
	if (!result.hasErrors()) {
	  repository.saveAndFlush(mydata);
	  res = new ModelAndView("redirect:/");
	} else {
	  mav.setViewName("index");
	  mav.addObject("msg", "sorry, error is occured...");
	  Iterable<MyData> list = repository.findAll();
	  mav.addObject("datalist", list);
	  res = mav;
	}
	return res;
  }
  
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(
	  @ModelAttribute MyData mydata,
      @PathVariable int id,
      ModelAndView mav) {
	mav.setViewName("edit");
	mav.addObject("title", "edit mydata.");
	Optional<MyData> data = repository.findById((long)id);
	mav.addObject("formModel", data.get());
	return mav;
  }
  
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView update(
	  @ModelAttribute MyData mydata,
      ModelAndView mav) {
    repository.saveAndFlush(mydata);
	return new ModelAndView("redirect:/");
  }
  
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public ModelAndView delete(
      @PathVariable int id,
      ModelAndView mav) {
	mav.setViewName("delete");
	mav.addObject("title", "delete mydata.");
	Optional<MyData> data = repository.findById((long)id);
	mav.addObject("formModel", data.get());
	return mav;
  }
  
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @Transactional(readOnly=false)
  public ModelAndView remove(
	  @RequestParam long id,
      ModelAndView mav) {
    repository.deleteById(id);
	return new ModelAndView("redirect:/");
  }  
  
  
  @PostConstruct
  public void init() {
  	
  	MyData d1 = new MyData();
  	d1.setName("tomoya");
  	d1.setAge(123);
  	d1.setMail("tomoya@gmail.com");
  	d1.setMemo("this is my data!");
  	repository.saveAndFlush(d1);
  	
  	MyData d2 = new MyData();
  	d2.setName("daisuke");
  	d2.setAge(24);
  	d2.setMail("daisuke@gmail.com");
  	d2.setMemo("yeaaaaaaa!!!");
  	repository.saveAndFlush(d2);
  	
  	MyData d3 = new MyData();
  	d3.setName("take");
  	d3.setAge(41);
  	d3.setMail("tttttake@gmail.com");
  	d3.setMemo("my friend");
  	repository.saveAndFlush(d3);
  	
  }
  
}


/* @RestController
public class HelloController {

	String[] names = {"rikako", "ayaka", "momona", "rina", "mizuki"};
	String[] mails = {"rikako@gmail.com", "ayaka@gmail.com", "momona@gmail.com", "rina@gmail.com", "mizuki@gmail.com"};
	
	@RequestMapping("/{id}")
	public DataObject index(@PathVariable int id) {
		return new DataObject(id, names[id], mails[id]);
	}
	
} */

/*class DataObject {
	private int id;
	private String name;
	private String value;
	
	public DataObject(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	} 
}*/
