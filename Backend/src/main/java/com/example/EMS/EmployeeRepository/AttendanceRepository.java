package com.example.EMS.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EMS.EmployeeEntity.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    Optional<Attendance>
    findByEmployee_EmployeeIdAndAttendanceDate(
            String employeeId,
            LocalDate attendanceDate);

    Optional<List<Attendance>> findAllByEmployee_EmployeeId(String employeeId);

    void deleteByEmployee_EmployeeId(String employeeId);
    
    @Query("""
            SELECT COALESCE(SUM(a.totalWorkingHours), 0)
            FROM Attendance a
            WHERE a.employee.id = :empId
            AND a.attendanceDate
            BETWEEN :startDate AND :endDate
            """)
    Double calculateWeeklyHours(

            @Param("empId") Long empId,

            @Param("startDate")
            LocalDate startDate,

            @Param("endDate")
            LocalDate endDate
    );
    
    
    @Query("""
            SELECT COUNT(a)
            FROM Attendance a
            WHERE a.attendanceDate = :date
            AND a.status = 'PRESENT'
            """)
     Long countPresentEmployees(
             @Param("date") LocalDate date
     );
    
    List<Attendance> findByEmployeeIdAndAttendanceDateBetween(
            Long empId,
            LocalDate startDate,
            LocalDate endDate
    );
}