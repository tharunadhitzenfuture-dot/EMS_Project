package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmployeePayroll {
	
	private double basicPay;
	private double HRA;
	private double specialAllowance;
	private double LTA;
	private double PF;
	private double medicalAllowance;
	private double bonus;
	
	public double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}
	public double getHRA() {
		return HRA;
	}
	public void setHRA(double hRA) {
		HRA = hRA;
	}
	public double getSpecialAllowance() {
		return specialAllowance;
	}
	public void setSpecialAllowance(double specialAllowance) {
		this.specialAllowance = specialAllowance;
	}
	public double getLTA() {
		return LTA;
	}
	public void setLTA(double lTA) {
		LTA = lTA;
	}
	public double getPF() {
		return PF;
	}
	public void setPF(double pF) {
		PF = pF;
	}
	public double getMedicalAllowance() {
		return medicalAllowance;
	}
	public void setMedicalAllowance(double medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	
	

}
