package com.internshiptracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Holds the in-memory list of internships and provides all the CRUD, search,
 * sort and statistics operations used by the menu.
 */
public class InternshipManager {

	private List<Internship> internships;
	private int nextId;

	public InternshipManager() {
		internships = FileHandler.loadData();
		nextId = computeNextId();
	}

	private int computeNextId() {
		int max = 0;
		for (Internship i : internships) {
			if (i.getId() > max)
				max = i.getId();
		}
		return max + 1;
	}

	public void save() {
		FileHandler.saveData(internships);
	}

	public Internship addInternship(String company, String role, String duration, double stipend, String status,
			String date, String email, String notes) {
		Internship internship = new Internship(nextId++, company, role, duration, stipend, status, date, email, notes);
		internships.add(internship);
		save();
		return internship;
	}

	public List<Internship> getAll() {
		return internships;
	}

	public Internship findById(int id) {
		for (Internship i : internships) {
			if (i.getId() == id)
				return i;
		}
		return null;
	}

	public boolean deleteById(int id) {
		Internship target = findById(id);
		if (target != null) {
			internships.remove(target);
			save();
			return true;
		}
		return false;
	}

	public boolean updateStatus(int id, String newStatus) {
		Internship target = findById(id);
		if (target != null) {
			target.setStatus(newStatus);
			save();
			return true;
		}
		return false;
	}

	public boolean updateInternship(int id, String company, String role, String duration, double stipend, String status,
			String date, String email, String notes) {
		Internship target = findById(id);
		if (target == null)
			return false;
		if (!company.isEmpty())
			target.setCompanyName(company);
		if (!role.isEmpty())
			target.setRole(role);
		if (!duration.isEmpty())
			target.setDuration(duration);
		if (stipend >= 0)
			target.setStipend(stipend);
		if (!status.isEmpty())
			target.setStatus(status);
		if (!date.isEmpty())
			target.setApplicationDate(date);
		if (!email.isEmpty())
			target.setContactEmail(email);
		if (!notes.isEmpty())
			target.setNotes(notes);
		save();
		return true;
	}

	public List<Internship> searchByCompany(String keyword) {
		List<Internship> result = new ArrayList<>();
		for (Internship i : internships) {
			if (i.getCompanyName().toLowerCase().contains(keyword.toLowerCase())) {
				result.add(i);
			}
		}
		return result;
	}

	public List<Internship> searchByStatus(String status) {
		List<Internship> result = new ArrayList<>();
		for (Internship i : internships) {
			if (i.getStatus().equalsIgnoreCase(status)) {
				result.add(i);
			}
		}
		return result;
	}

	public List<Internship> sortByStipendDescending() {
		List<Internship> sorted = new ArrayList<>(internships);
		sorted.sort(Comparator.comparingDouble(Internship::getStipend).reversed());
		return sorted;
	}

	public List<Internship> sortByCompanyName() {
		List<Internship> sorted = new ArrayList<>(internships);
		sorted.sort(Comparator.comparing(Internship::getCompanyName, String.CASE_INSENSITIVE_ORDER));
		return sorted;
	}

	/**
	 * Returns a simple text summary: counts per status and total/average stipend.
	 */
	public String getStatistics() {
		if (internships.isEmpty()) {
			return "No records available yet.";
		}
		int applied = 0, interview = 0, offered = 0, rejected = 0, completed = 0, other = 0;
		double totalStipend = 0;
		for (Internship i : internships) {
			totalStipend += i.getStipend();
			switch (i.getStatus().trim().toLowerCase()) {
			case "applied":
				applied++;
				break;
			case "interview":
				interview++;
				break;
			case "offered":
				offered++;
				break;
			case "rejected":
				rejected++;
				break;
			case "completed":
				completed++;
				break;
			default:
				other++;
			}
		}
		double avgStipend = totalStipend / internships.size();

		StringBuilder sb = new StringBuilder();
		sb.append("Total internships tracked : ").append(internships.size()).append("\n");
		sb.append("  Applied    : ").append(applied).append("\n");
		sb.append("  Interview  : ").append(interview).append("\n");
		sb.append("  Offered    : ").append(offered).append("\n");
		sb.append("  Rejected   : ").append(rejected).append("\n");
		sb.append("  Completed  : ").append(completed).append("\n");
		if (other > 0)
			sb.append("  Other      : ").append(other).append("\n");
		sb.append(String.format("Average stipend            : %.2f%n", avgStipend));
		sb.append(String.format("Total stipend (all records): %.2f%n", totalStipend));
		return sb.toString();
	}
}
