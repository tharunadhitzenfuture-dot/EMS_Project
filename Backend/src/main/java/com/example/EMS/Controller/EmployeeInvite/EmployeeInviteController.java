package com.example.EMS.Controller.EmployeeInvite;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.example.EMS.Entity.EmployeeInvite;
import com.example.EMS.Service.EmployeeInvite.EmployeeInviteService;

@RestController
@RequestMapping("/api/employeeInvite")
@RequiredArgsConstructor
public class EmployeeInviteController {
	
	private final EmployeeInviteService empInviteService;

	@PostMapping("/save")
	public ResponseEntity<?> saveEmployee(@RequestPart("empInvite") EmployeeInvite empInvite, 
			@RequestPart(value= "file", required=false) MultipartFile file,
			
			@RequestPart(value= "aadhar", required=false) MultipartFile aadhar,
			@RequestPart(value= "pan_card", required=false) MultipartFile pan_card,
			@RequestPart(value= "higherEducation", required=false) List<MultipartFile> higherEducation,
			@RequestPart(value= "bankStatement", required=false) List<MultipartFile> bankStatement,
			@RequestPart(value= "salarySlip", required=false) List<MultipartFile> salarySlip,
			
			@RequestPart(value= "passbook",required=false) MultipartFile passbook,
			@RequestPart(value= "education",required=false) MultipartFile education,
			@RequestPart(value="resume",required=false) MultipartFile resume,
			@RequestPart(value="offerLetter",required=false) MultipartFile offerLetter,
			@RequestPart(value="prevExpLetter",required=false) List<MultipartFile> prevExpLetter,
			@RequestPart(value="experienceLetter",required=false) List<MultipartFile> experienceLetter){
		
		if (file != null && !file.isEmpty()) {

		    String fileType = file.getOriginalFilename();

		    boolean isPdfExtension =
	                fileType != null &&
	                fileType.toLowerCase().endsWith(".jpeg") ||
	                fileType.toLowerCase().endsWith(".jpg") ||
	                fileType.toLowerCase().endsWith(".png") ;
		 

		    if (!isPdfExtension) {

		        return ResponseEntity.badRequest()
		                .body("Only JPG, JPEG, PNG images are allowed for profile");
		    }
		}
		

		if(aadhar != null && !aadhar.isEmpty()) {
			String fileType = aadhar.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload aadhar pdf format");
			 }
		}
		
		if(pan_card != null && !pan_card.isEmpty()) {
			String fileType = pan_card.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload pan card pdf format");
			 }
		}
		
		if(passbook != null && !passbook.isEmpty()) {
			String fileType = passbook.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload passbook pdf format");
			 }
		}
		
		if(education != null && !education.isEmpty()) {
			String fileType = education.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload education pdf format");
			 }
		}
		
		if(resume != null && !resume.isEmpty()) {
			String fileType = resume.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload resume pdf format");
			 }
		}
		
		if(offerLetter != null && !offerLetter.isEmpty()) {
			String fileType = offerLetter.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Offer Letter pdf format");
			 }
		}
		
		if (salarySlip != null && !salarySlip.isEmpty()) {

		    for (MultipartFile pdf : salarySlip) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload salary slip pdf format");
				 }
		    }
		}
		
		if (higherEducation != null && !higherEducation.isEmpty()) {

		    for (MultipartFile pdf : higherEducation) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload higher education pdf format");
				 }
		    }
		}
		
		
		if (bankStatement != null && !bankStatement.isEmpty()) {

		    for (MultipartFile pdf : bankStatement) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload bank statement pdf format");
				 }
		    }
		}
		

		if (prevExpLetter != null && !prevExpLetter.isEmpty()) {

		    for (MultipartFile pdf : prevExpLetter) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload previous offer letters pdf format");
				 }
		    }
		}
		
		if (experienceLetter != null && !experienceLetter.isEmpty()) {

		    for (MultipartFile pdf : experienceLetter) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload experience letter pdf format");
				 }
		    }
		}
		
		return empInviteService.saveEmployee(empInvite, file,aadhar,pan_card, higherEducation,bankStatement, salarySlip, passbook, education, resume, offerLetter, prevExpLetter, experienceLetter);
	}
	
	@GetMapping("/getAllform")
	public ResponseEntity<?> getAllForm(){
		return empInviteService.getAllForm();
	}
	
	
	@GetMapping("/getForm/{id}")
	public ResponseEntity<?> getFormById(@PathVariable Long id){
		return empInviteService.getFormById(id);
	}
	
	@DeleteMapping("/deleteForm/{id}")
	public ResponseEntity<?> deleteFormById(@PathVariable Long id){
		return empInviteService.deleteFormById(id);
	}
	
	@PostMapping("/convertList")
	public ResponseEntity<?> convertDataList(@RequestBody List<Long> empId){
		return empInviteService.convert(empId);
	}
	
	@PostMapping("/convert/{id}")
	public ResponseEntity<?> convertData(@PathVariable Long id){
		return empInviteService.convertByOne(id);
	}
	
	
	@PutMapping("/updateForm/{id}")
	public ResponseEntity<?> updateFormById(@PathVariable Long id, @RequestPart("empInvite") EmployeeInvite empInvite, 
			@RequestPart(value= "file", required=false) MultipartFile file,
			
			@RequestPart(value= "aadhar", required=false) MultipartFile aadhar,
			@RequestPart(value= "pan_card", required=false) MultipartFile pan_card,
			@RequestPart(value= "higherEducation", required=false) List<MultipartFile> higherEducation,
			@RequestPart(value= "bankStatement", required=false) List<MultipartFile> bankStatement,
			@RequestPart(value= "salarySlip", required=false) List<MultipartFile> salarySlip,
			
			@RequestPart(value= "passbook",required=false) MultipartFile passbook,
			@RequestPart(value= "education",required=false) MultipartFile education,
			@RequestPart(value="resume",required=false) MultipartFile resume,
			@RequestPart(value="offerLetter",required=false) MultipartFile offerLetter,
			@RequestPart(value="prevExpLetter",required=false) List<MultipartFile> prevExpLetter,
			@RequestPart(value="experienceLetter",required=false) List<MultipartFile> experienceLetter) throws Exception{
		
		if (file != null && !file.isEmpty()) {

		    String fileType = file.getOriginalFilename();

		    boolean isPdfExtension =
	                fileType != null &&
	                fileType.toLowerCase().endsWith(".jpeg") ||
	                fileType.toLowerCase().endsWith(".jpg") ||
	                fileType.toLowerCase().endsWith(".png") ;
		 

		    if (!isPdfExtension) {

		        return ResponseEntity.badRequest()
		                .body("Only JPG, JPEG, PNG images are allowed for profile");
		    }
		}
		

		if(aadhar != null && !aadhar.isEmpty()) {
			String fileType = aadhar.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload aadhar pdf format");
			 }
		}
		
		if(pan_card != null && !pan_card.isEmpty()) {
			String fileType = pan_card.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload pan card pdf format");
			 }
		}
		
		if(passbook != null && !passbook.isEmpty()) {
			String fileType = passbook.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload passbook pdf format");
			 }
		}
		
		if(education != null && !education.isEmpty()) {
			String fileType = education.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload education pdf format");
			 }
		}
		
		if(resume != null && !resume.isEmpty()) {
			String fileType = resume.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload resume pdf format");
			 }
		}
		
		if(offerLetter != null && !offerLetter.isEmpty()) {
			String fileType = offerLetter.getOriginalFilename();

			 boolean isPdfExtension =
		                fileType != null &&
		                fileType.toLowerCase().endsWith(".pdf");
			 
			 if(!isPdfExtension) {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Offer Letter pdf format");
			 }
		}
		
		if (salarySlip != null && !salarySlip.isEmpty()) {

		    for (MultipartFile pdf : salarySlip) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload salary slip pdf format");
				 }
		    }
		}
		
		if (higherEducation != null && !higherEducation.isEmpty()) {

		    for (MultipartFile pdf : higherEducation) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload higher education pdf format");
				 }
		    }
		}
		
		
		if (bankStatement != null && !bankStatement.isEmpty()) {

		    for (MultipartFile pdf : bankStatement) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload bank statement pdf format");
				 }
		    }
		}
		

		if (prevExpLetter != null && !prevExpLetter.isEmpty()) {

		    for (MultipartFile pdf : prevExpLetter) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload previous offer letters pdf format");
				 }
		    }
		}
		
		if (experienceLetter != null && !experienceLetter.isEmpty()) {

		    for (MultipartFile pdf : experienceLetter) {
		    	String fileType = pdf.getOriginalFilename();

				 boolean isPdfExtension =
			                fileType != null &&
			                fileType.toLowerCase().endsWith(".pdf");
				 
				 if(!isPdfExtension) {
					 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload experience letter pdf format");
				 }
		    }
		}
		
		return empInviteService.updateFormById(id, empInvite, file, aadhar, pan_card, higherEducation,bankStatement,salarySlip, passbook, education, resume, offerLetter,prevExpLetter, experienceLetter);
	}
	
	
	
	
	
	
}
