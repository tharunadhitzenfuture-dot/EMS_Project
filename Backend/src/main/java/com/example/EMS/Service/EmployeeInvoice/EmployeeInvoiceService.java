package com.example.EMS.Service.EmployeeInvoice;

import com.example.EMS.Entity.EmployeeInvoice;
import org.springframework.http.ResponseEntity;

public interface EmployeeInvoiceService {

    ResponseEntity<?> createInvoice(EmployeeInvoice invoice);

    ResponseEntity<?> getAllInvoice();

    ResponseEntity<?> getInvoiceById(String id);

    ResponseEntity<?> deleteByInvoiceId(String id);

    ResponseEntity<?> updateInvoice(EmployeeInvoice empInvoice, String id);

}