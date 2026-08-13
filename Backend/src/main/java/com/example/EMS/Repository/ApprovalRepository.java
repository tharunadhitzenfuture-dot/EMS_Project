package com.example.EMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EMS.Entity.ApprovalSystem;

public interface ApprovalRepository extends JpaRepository<ApprovalSystem, Long> {

	@Query("""
		       SELECT a
		       FROM ApprovalSystem a
		       WHERE a.approverEmail1 = :email
		          OR a.approverEmail2 = :email
		       """)
		List<ApprovalSystem> findByApproverEmail(@Param("email") String email);
}
