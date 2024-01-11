package com.example.emp.controller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.emp.domain.Student;
import com.example.emp.domain.User;
import com.example.emp.service.StudentService;
import com.example.emp.service.UserService;


@Controller
public class StudentController {
    
     @Autowired
        private StudentService service;
     
     @Autowired
     private UserService userservice;
     
    
     
        @GetMapping("/index")
        public String viewHomePage(Model model) {
            List<Student> liststudent = service.listAll();
            model.addAttribute("liststudent", liststudent);
            System.out.print("Get / ");    
            return "index";
        }
        @GetMapping("/new")
        public String add(Model model) {
            model.addAttribute("student", new Student());
            return "new";
        }
        @RequestMapping(value = "/save", method = RequestMethod.POST)
        public String saveStudent(@ModelAttribute("student") Student std) {
            service.save(std);
            //producer.sendMsgToTopic(std.getStudentname());
            return "redirect:/index";
        }
        @RequestMapping("/edit/{id}")
        public ModelAndView showEditStudentPage(@PathVariable(name = "id") int id) {
            ModelAndView mav = new ModelAndView("new");
            Student std = service.get(id);
            mav.addObject("student", std);
            return mav;
            
        }
        @RequestMapping("/delete/{id}")
        public String deletestudent(@PathVariable(name = "id") int id) {
            service.delete(id);
            return "redirect:/index";
        }
        
        @GetMapping("/producerMsg")
    	public String getMessageFromClient(@RequestParam("message") String message) {
    		
    		//producer.sendMsgToTopic(message);
    		 return "login";
    	}
        @GetMapping("/")
        public String login(Model model) {
        	 model.addAttribute("user", new User());
        	 System.out.print("Login Page.................."); 
          return "login";
        }
        @SuppressWarnings("null")
		@RequestMapping(value = "/loginChk", method = RequestMethod.POST)
        public String loginChk(@ModelAttribute("user") User user) throws IOException {
        	 List<User> listuser = userservice.listAll();
  
        	 String loginUser=user.getUsername();
        	 String loginPassword=user.getPassword();
        	 System.out.print("Login User.................."+loginUser+loginPassword); 
        	 String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        	    String str
        	        = " User "+loginUser+" has trying to login on "+timeStamp;
        	
        	    PrintWriter out = null;
        	    BufferedWriter bw = null;
        	    FileWriter fw = null;
        	    try{
        	       fw = new FileWriter("C:\\Users\\KafkaApp\\log\\loginUser.txt", true);
        	       bw = new BufferedWriter(fw);
        	       out = new PrintWriter(bw);
        	       out.println(str);
        	    }
        	    catch( IOException e ){
        	       // File writing/opening failed at some stage.
        	    }
        	    finally{
        	       try{
        	          if( out != null ){
        	             out.close(); // Will close bw and fw too
        	          }
        	          else if( bw != null ){
        	             bw.close(); // Will close fw too
        	          }
        	          else if( fw != null ){
        	             fw.close();
        	          }
        	          else{
        	             // Oh boy did it fail hard! :3
        	          }
        	       }
        	       catch( IOException e ){
        	          // Closing the file writers failed for some obscure reason
        	       }
        	    }
        	 
        	 for (User usr: listuser) {
        		if(loginUser.equalsIgnoreCase(usr.getUsername())) {
        		    	if(loginPassword.equalsIgnoreCase(usr.getPassword())) {
        		    		return "redirect:/index";
        		    	}
        		    }
        		}
           // return "redirect:/";
            return "redirect:Login?error";
        }
}
