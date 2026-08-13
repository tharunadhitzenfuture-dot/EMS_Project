package com.example.EMS.Controller.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeDTO.ApproverResponseDTO;
import com.example.EMS.Entity.ApprovalSystem;
import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.User;
import com.example.EMS.Repository.ApprovalRepository;
import com.example.EMS.Repository.EmpRepository;
import com.example.EMS.Service.EmployeeService.EmpService;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmpController {
	
	public EmpService empService;
	public EmpRepository empRepository;
	public ApprovalRepository approvalRepository;


	@PostMapping("/register")
	public ResponseEntity<?> createUserControll(@RequestBody Employee emp){
		return empService.createUser(emp);
		
	}
	
	@GetMapping("/GetApprovalList")
	public ResponseEntity<?> getApprovalList(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		Optional<Employee> empUser = empRepository.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
		}
		
		String email = empUser.get().getEmail();
		List<ApprovalSystem> res = approvalRepository.findByApproverEmail(email);
		
		if(res.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You dont have approval list");
		}
		return ResponseEntity.ok(res);
		

	}
	
	
	@PostMapping("/registerEmp")
	public ResponseEntity<?> createUserImg(@RequestPart("employee") Employee emp, 
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
		
		
		
		return empService.createEmpIMG(emp, file,aadhar,pan_card, higherEducation,bankStatement, salarySlip, passbook, education, resume, offerLetter, prevExpLetter, experienceLetter);
	}
	
	
	@GetMapping("/getData")
	public ResponseEntity<?> getAllEmployeeDetails(){
		
		return empService.getAllEmployeeDetails();
	}
	
	@GetMapping("/getPayroll/{empId}")
	public ResponseEntity<?> getEmployeePayrollById(@PathVariable String empId){
		return empService.getPayrollById(empId);
		
	}
	
	@GetMapping("/getEmployee/{empId}")
	public ResponseEntity<?> getEmployeeById(@PathVariable String empId){
		return empService.getEmployeeById(empId);
		
	}
	
	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable String empId){
		return empService.deleteEmployeeById(empId);
		
	}
	
	@PutMapping(value = "/updateEmployee/{empId}", consumes = "multipart/form-data")
	public ResponseEntity<?> updateEmployee(

	        @PathVariable String empId,
	        @RequestPart(value = "employee", required = false) Employee emp,
	        @RequestPart(value = "file", required = false) MultipartFile file,
	        @RequestPart(value = "aadhar", required = false) MultipartFile aadhar,
	        @RequestPart(value = "pan_card", required = false) MultipartFile pan_card,
	        @RequestPart(value = "higherEducation", required = false) List<MultipartFile> higherEducation,
	        @RequestPart(value="prevExpLetter",required=false) List<MultipartFile> prevExpLetter,
	        @RequestPart(value = "bankStatement", required = false)
	        List<MultipartFile> bankStatement,
	        
	        @RequestPart(value = "salarySlip", required = false)
	        List<MultipartFile> salarySlip,

	        @RequestPart(value = "passbook", required = false)
	        MultipartFile passbook,

	        @RequestPart(value = "education", required = false)
	        MultipartFile education,

	        @RequestPart(value = "resume", required = false)
	        MultipartFile resume,

	        @RequestPart(value = "offerLetter", required = false)
	        MultipartFile offerLetter,

	        @RequestPart(value = "experienceLetter", required = false)
	        List<MultipartFile> experienceLetter

	) throws Exception {
		
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

	    return empService.updateEmployeeAll(
	            empId,
	            emp,
	            file,
	            aadhar,
	            pan_card,
	            higherEducation,
	            prevExpLetter,
	            bankStatement,
	            salarySlip,
	            passbook,
	            education,
	            resume,
	            offerLetter,
	            experienceLetter
	    );
	
	}
	
	@PostMapping(value = "/uploadExcel", consumes = "multipart/form-data")
	public ResponseEntity<?> createUserXL(

	        @RequestPart(value = "xlFile")
	        MultipartFile xlFile,

	        @RequestPart(value = "file", required = false)
	        List<MultipartFile> file,

	        @RequestPart(value = "aadhar", required = false)
	        List<MultipartFile> aadhar,

	        @RequestPart(value = "pan_card", required = false)
	        List<MultipartFile> pan_card,

	        @RequestPart(value = "higherEducation", required = false)
	        List<MultipartFile> higherEducation,

	        @RequestPart(value = "bankStatement", required = false)
	        List<MultipartFile> bankStatement,

	        @RequestPart(value = "salarySlip", required = false)
	        List<MultipartFile> salarySlip,

	        @RequestPart(value = "passbook", required = false)
	        List<MultipartFile> passbook,

	        @RequestPart(value = "education", required = false)
	        List<MultipartFile> education,

	        @RequestPart(value = "resume", required = false)
	        List<MultipartFile> resume,

	        @RequestPart(value = "offerLetter", required = false)
	        List<MultipartFile> offerLetter,

	        @RequestPart(value = "prevExpLetter", required = false)
	        List<MultipartFile> prevExpLetter,
	        
	        @RequestPart(value="higherCertification", required=false) 
	        List<MultipartFile> higherCertification,

	        @RequestPart(value = "experienceLetter", required = false)
	        List<MultipartFile> experienceLetter) {

	    return empService.createUserXL(
	            xlFile,
	            file,
	            aadhar,
	            pan_card,
	            higherEducation,
	            bankStatement,
	            salarySlip,
	            passbook,
	            education,
	            resume,
	            offerLetter,
	            prevExpLetter,
	            higherCertification,
	            experienceLetter);
	}
	
	@PatchMapping(value = "/updateExcel", consumes = "multipart/form-data")
	public ResponseEntity<?> updateUserXL(

	        @RequestPart(value = "xlFile", required = false)
	        MultipartFile xlFile,

	        @RequestPart(value = "file", required = false)
	        List<MultipartFile> file,

	        @RequestPart(value = "aadhar", required = false)
	        List<MultipartFile> aadhar,

	        @RequestPart(value = "pan_card", required = false)
	        List<MultipartFile> pan_card,

	        @RequestPart(value = "passbook", required = false)
	        List<MultipartFile> passbook,

	        @RequestPart(value = "education", required = false)
	        List<MultipartFile> education,

	        @RequestPart(value = "higherEducation", required = false)
	        List<MultipartFile> higherEducation,

	        @RequestPart(value = "resume", required = false)
	        List<MultipartFile> resume,

	        @RequestPart(value = "offerLetter", required = false)
	        List<MultipartFile> offerLetter,

	        @RequestPart(value = "prevExpLetter", required = false)
	        List<MultipartFile> prevExpLetter,

	        @RequestPart(value = "experienceLetter", required = false)
	        List<MultipartFile> experienceLetter,

	        @RequestPart(value = "bankStatement", required = false)
	        List<MultipartFile> bankStatement,
	        
	        @RequestPart(value="higherCertification", required=false) 
	        List<MultipartFile> higherCertification,

	        @RequestPart(value = "salarySlip", required = false)
	        List<MultipartFile> salarySlip

	) {

	    return empService.updateUserXL(
	            xlFile,
	            file,
	            aadhar,
	            pan_card,
	            passbook,
	            education,
	            higherEducation,
	            resume,
	            offerLetter,
	            prevExpLetter,
	            experienceLetter,
	            bankStatement,
	            higherCertification,
	            salarySlip
	    );
	}
	
	@GetMapping("/search")
	public List<ApproverResponseDTO> searchEmployee(@RequestParam String keyword){
		List<Employee> lst =  empRepository.searchByFirstName(keyword);
		List<ApproverResponseDTO> res = new ArrayList<>();
		for(Employee emp: lst) {
			ApproverResponseDTO dto = new ApproverResponseDTO();
			dto.setEmpId(emp.getEmployeeId());
			dto.setEmail(emp.getEmail());
			dto.setName(emp.getFirst_name()+" "+emp.getLast_name());		
			res.add(dto);
		}
		return res;
		
	}
	
	
	
	
	
	
	
	
	

}
