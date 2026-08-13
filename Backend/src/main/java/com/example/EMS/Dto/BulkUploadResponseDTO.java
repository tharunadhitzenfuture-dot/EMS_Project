package com.example.EMS.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BulkUploadResponseDTO {

    private int totalRows;
    private int successCount;
    private int failedCount;

    private List<String> success = new ArrayList<>();
    private List<String> failed = new ArrayList<>();
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}
	public List<String> getSuccess() {
		return success;
	}
	public void setSuccess(List<String> success) {
		this.success = success;
	}
	public List<String> getFailed() {
		return failed;
	}
	public void setFailed(List<String> failed) {
		this.failed = failed;
	}

    
}
