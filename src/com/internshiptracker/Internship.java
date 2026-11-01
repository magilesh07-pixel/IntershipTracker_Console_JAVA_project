package com.internshiptracker;

import java.io.Serializable;

/**
 * Model class representing a single internship record.
 */
public class Internship implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String companyName;
	private String role;
	private String duration; // e.g. "3 months"
	private double stipend; // monthly stipend
	private String status; // Applied, Interview, Offered, Rejected, Completed
	private String applicationDate; // dd-MM-yyyy (stored as text for simplicity)
	private String contactEmail;
	private String notes;

	public Internship(int id, String companyName, String role, String duration, double stipend, String status,
			String applicationDate, String contactEmail, String notes) {
		this.id = id;
		this.companyName = companyName;
		this.role = role;
		this.duration = duration;
		this.stipend = stipend;
		this.status = status;
		this.applicationDate = applicationDate;
		this.contactEmail = contactEmail;
		this.notes = notes;
	}

	// ----- Getters -----
	public int getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getRole() {
		return role;
	}

	public String getDuration() {
		return duration;
	}

	public double getStipend() {
		return stipend;
	}

	public String getStatus() {
		return status;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getNotes() {
		return notes;
	}

	// ----- Setters -----
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setStipend(double stipend) {
		this.stipend = stipend;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Converts this record into a single pipe-delimited line for file storage. Any
	 * '|' characters inside free-text fields are replaced to avoid breaking the
	 * format.
	 */
	public String toFileLine() {
		return id + "|" + safe(companyName) + "|" + safe(role) + "|" + safe(duration) + "|" + stipend + "|"
				+ safe(status) + "|" + safe(applicationDate) + "|" + safe(contactEmail) + "|" + safe(notes);
	}

	private String safe(String s) {
		return (s == null) ? "" : s.replace("|", "/");
	}

	/**
	 * Parses a pipe-delimited line (as produced by toFileLine) back into an
	 * Internship object.
	 */
	public static Internship fromFileLine(String line) {
		String[] p = line.split("\\|", -1);
		int id = Integer.parseInt(p[0]);
		String companyName = p[1];
		String role = p[2];
		String duration = p[3];
		double stipend = Double.parseDouble(p[4]);
		String status = p[5];
		String applicationDate = p[6];
		String contactEmail = p[7];
		String notes = p.length > 8 ? p[8] : "";
		return new Internship(id, companyName, role, duration, stipend, status, applicationDate, contactEmail, notes);
	}

	@Override
	public String toString() {
		return String.format(
				"ID: %-4d | Company: %-18s | Role: %-16s | Duration: %-10s | Stipend: %-9.2f | Status: %-10s | Applied: %-10s | Email: %-20s | Notes: %s",
				id, companyName, role, duration, stipend, status, applicationDate, contactEmail, notes);
	}
}
