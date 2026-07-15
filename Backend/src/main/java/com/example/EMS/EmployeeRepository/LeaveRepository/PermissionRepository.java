package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.enums.LeaveStatus;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
	List<Permission> findByEmployeeIdAndStartDateAndEndDate(
	        Long empId,
	        LocalDate startDate,
	        LocalDate endDate);
	
	Optional<Permission> findByPermissionDateAndEmployee_Id(LocalDate date,Long id);
	List<Permission> findByEmployeeId(Long id);
	
	 @Query("""
  	       SELECT l
  	       FROM Permission l
  	       WHERE (l.approverEmail1 = :email
  	              OR l.approverEmail2 = :email)
  	         AND l.status = :status
  	       """)
  	List<Permission> findByApproverEmailAndStatus(
  	        @Param("email") String email,
  	        @Param("status") LeaveStatus status);

}
