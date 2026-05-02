package com.example.EMS.EmployeeService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeEntity.BankDetails;
import com.example.EMS.EmployeeEntity.Education;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Experience;
import com.example.EMS.EmployeeEntity.ProfessionalDetails;
import com.example.EMS.EmployeeRepository.EmpRepository;

@Service
public class EmpService {
	
	public EmpRepository empRepo;
	public PasswordEncoder passwordEncoder;
	
	
	
	public EmpService(EmpRepository empRepo, PasswordEncoder passwordEncoder) {
		this.empRepo = empRepo;
		this.passwordEncoder = passwordEncoder;
	}



	public ResponseEntity<?> createUser(@RequestBody Employee emp){
		
		if(emp.getEmail() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter employee mail id"); 
		}
		
		Optional<Employee> emailuser = empRepo.findByEmail(emp.getEmail());
		
		if(emailuser.isPresent()) {
			return ResponseEntity.status(409).body("User Already exists");
		}
		
		Employee employee = empRepo.save(emp);
		return ResponseEntity.ok(employee);
		
	}
	
	public double calculateAnnualCTC(double basicPay,double HRA, double specialAllowance,double LTA,double PF,double medicalAllowance, double bonus) {
		 double monthlyCTC = basicPay + HRA + specialAllowance + LTA + PF + medicalAllowance + bonus;
		  return monthlyCTC * 12;
		
	}
	
	public String saveFile(MultipartFile file, String folder) throws Exception{
		String upload = System.getProperty("user.dir") + "/"+ folder + "/";
		File dir = new File(upload);
		
		if(!dir.exists()) dir.mkdirs();
		
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File destination = new File(upload + fileName);
		file.transferTo(destination);
		return folder + "/" +fileName;
		
	}
	
	public ResponseEntity<?> createEmpIMG(@RequestPart("employee") Employee emp, 
			@RequestPart(value= "file", required=true) MultipartFile file,
			@RequestPart(value= "passbook", required=true) MultipartFile passbook,
			@RequestPart(value= "education", required= true) MultipartFile education,
			@RequestPart(value="resume", required= true) MultipartFile resume,
			@RequestPart(value="offerLetter", required= true) MultipartFile offerLetter,
			@RequestPart(value="experienceLetter", required= true) List<MultipartFile> experienceLetter){
		
		Optional<Employee> empId = empRepo.findByEmployeeId(emp.getEmployeeId());
		if(empId.isPresent()) {
			return ResponseEntity.status(409).body("User Already exists with Employee Id: "+ empId.get().getEmployeeId());
		}
		
		Optional<Employee> emailuser = empRepo.findByEmail(emp.getEmail());
		
		if(emailuser.isPresent()) {
			return ResponseEntity.status(409).body("User Already exists with Email: "+ emailuser.get().getEmail());
		}
		
		Long maxId = empRepo.findMaxId();
		long nextId = (maxId == null) ? 1 : maxId + 1;

	    emp.setEmployeeId(String.format("ZF%03d", nextId));
		
		
		if(file != null && !file.isEmpty()) {
			try {
				String fileName = saveFile(file, "uploads");
				emp.setImgFile(fileName);

			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Image upload failed "+e);
			}
		}
		
		if(passbook != null && !passbook.isEmpty()) {
			try {
				
				String fileName = saveFile(passbook, "uploadsPdf");
				if(emp.getBankDetails() == null) {
					emp.setBankDetails(new BankDetails());
				}
				
				emp.getBankDetails().setPassbook_pdf(fileName);
				
				
			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Passbook Pdf upload failed "+e);
			}
		}
		
		if(education != null && !education.isEmpty()) {
			try {
				
				String fileName = saveFile(education, "uploadsPdf");
				if(emp.getEducation() == null) {
					emp.setEducation(new Education());
				}
				
				emp.getEducation().setEducation_pdf(fileName);
				
			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Educational Pdf upload failed "+e);
			}
		}
		
		
		if(resume != null && !resume.isEmpty()) {
			try {
				String fileName = saveFile(resume, "uploadsPdf");
				if(emp.getProfessional_details() == null) {
					emp.setProfessional_details(new ProfessionalDetails());
				}
				
				emp.getProfessional_details().setResume(fileName);
			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Resume Pdf upload failed "+ e);
			}
		}
		
		if(offerLetter != null && !offerLetter.isEmpty()) {
			try {
				String fileName = saveFile(offerLetter, "uploadsPdf");
				if(emp.getProfessional_details() == null) {
					emp.setProfessional_details(new ProfessionalDetails());
				}
				
				emp.getProfessional_details().setOffer_letter(fileName);
			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Offer Letter Pdf upload failed: "+ e);
			}
		}
		
		if (experienceLetter != null && !experienceLetter.isEmpty()) {
		    try {
		        if (emp.getExperience() == null) {
		            emp.setExperience(new ArrayList<>());
		        }

		        for (int i = 0; i < experienceLetter.size(); i++) {
		            MultipartFile files = experienceLetter.get(i);
		            String fileName = saveFile(files, "uploadsPdf");
		            Experience exp;
		            if (emp.getExperience().size() > i) {
		                exp = emp.getExperience().get(i);
		            } else {
		                exp = new Experience();
		                emp.getExperience().add(exp);
		            }

		            exp.setExp_letter(fileName);
		        }

		    } catch (Exception e) {
		        return ResponseEntity.status(500).body("Experience upload failed "+e);
		    }
		}
		
		double basic = emp.getEmpPayroll().getBasicPay();
		double hra = emp.getEmpPayroll().getHRA();
		double specialAllowance = emp.getEmpPayroll().getSpecialAllowance();
		double lta = emp.getEmpPayroll().getLTA();
		double pf = emp.getEmpPayroll().getPF();
		double medical = emp.getEmpPayroll().getMedicalAllowance();
		double bonus = emp.getEmpPayroll().getBonus();
		double ctc = calculateAnnualCTC(basic,hra,specialAllowance,lta,pf,medical,bonus);
		emp.getEmpPayroll().setAnnualCTC(ctc);

		Employee employee = empRepo.save(emp);
		return ResponseEntity.ok(employee);
		
	}
	
	public ResponseEntity<?> getAllEmployeeDetails(){
		List<Employee> list = empRepo.findAll();
		return ResponseEntity.ok(list);
	}
	
	public ResponseEntity<?> getPayrollById(String empId){
		 Optional<Employee> empOptional = empRepo.findByEmployeeId(empId);

		 if (empOptional.isEmpty()) {
		        return ResponseEntity.status(404).body("Employee not found with ID: " + empId);
		    }

		  Employee emp = empOptional.get();

		 if (emp.getEmpPayroll() == null) {
		        return ResponseEntity.status(404).body("Payroll details not found for Employee ID: " + empId);
		  }

		    return ResponseEntity.ok(emp.getEmpPayroll());
	}
	
	public ResponseEntity<?> getEmployeeById(Long id){
		
		Optional<Employee> emp = empRepo.findById(id);
		if(emp.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(emp);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id: "+id+" not found");
	}
		
	@Transactional
	public ResponseEntity<?> deleteEmployeeById(String id){
		
		Optional<Employee> emp = empRepo.findByEmployeeId(id);
		if(emp.isPresent()) {
			empRepo.deleteByEmployeeId(id);
			return ResponseEntity.status(HttpStatus.FOUND).body("Employee deleted with id: "+id);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id: "+id+" not found");
	}
		
	

}
