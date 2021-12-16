package com.antra.boot.restcontroller;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("rest controller for file uploading")
public class FileRestController {
	@GetMapping("new")
	public ModelAndView jsp() {
		ModelAndView mav=new ModelAndView("/WEB-INF/views/new.jsp");
		return mav;
	}
	
	
	
	@PostMapping(value="/upload")
	@ApiOperation("this is for uploading a file")
	public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file) {
		FileOutputStream fout=null;
		try {
			String name=file.getOriginalFilename();
			File f=new File(name);
			if(f.exists()) {
				System.out.println("the file with name already exist");
			}
			else {
				f.createNewFile();
			}
			byte[] b=file.getBytes();
			fout=new FileOutputStream("D:\\"+name);
			fout.write(b);
			String message = "Uploaded the file successfully: " + name;
		      return ResponseEntity.status(HttpStatus.OK).body(message);
		}catch (Exception e){
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("there is error uploading the file");
		}
		finally {
			if(fout!=null)
				try {
				fout.close();}
			catch (Exception e) {
				return ResponseEntity.status(HttpStatus.CHECKPOINT).body("internal error file write unavailable");
			}
		}
	}

}
