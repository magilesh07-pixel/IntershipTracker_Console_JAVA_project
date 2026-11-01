package com.internshiptracker;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Internship Tracker console application. Presents a menu
 * and dispatches to the InternshipManager for all operations.
 */
public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static InternshipManager manager = new InternshipManager();

	public static void main(String[] args) {
		System.out.println("=====================================");
		System.out.println("      INTERNSHIP TRACKER (Console)  ");
		System.out.println("=====================================");

		boolean running = true;
		while (running) {
			printMenu();
			String choice = sc.nextLine().trim();
			switch (choice) {
			case "1":
				addInternship();
				break;
			case "2":
				viewAll();
				break;
			case "3":
				searchMenu();
				break;
			case "4":
				updateStatus();
				break;
			case "5":
				updateFull();
				break;
			case "6":
				deleteInternship();
				break;
			case "7":
				sortMenu();
				break;
			case "8":
				showStatistics();
				break;
			case "9":
				running = false;
				System.out.println("Data saved. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please enter a number from 1-9.");
			}
		}
		sc.close();
	}

	private static void printMenu() {
		System.out.println("\n----------- MENU -----------");
		System.out.println("1. Add new internship");
		System.out.println("2. View all internships");
		System.out.println("3. Search internships");
		System.out.println("4. Update status");
		System.out.println("5. Update full record");
		System.out.println("6. Delete internship");
		System.out.println("7. Sort internships");
		System.out.println("8. View statistics");
		System.out.println("9. Exit");
		System.out.print("Enter your choice: ");
	}

	private static void addInternship() {
		System.out.println("\n--- Add New Internship ---");
		String company = prompt("Company name: ");
		String role = prompt("Role/Position: ");
		String duration = prompt("Duration (e.g. 3 months): ");
		double stipend = promptDouble("Stipend (monthly, numeric): ");
		String status = promptStatus();
		String date = prompt("Application date (dd-MM-yyyy): ");
		String email = prompt("Contact email: ");
		String notes = prompt("Notes (optional): ");

		Internship created = manager.addInternship(company, role, duration, stipend, status, date, email, notes);
		System.out.println("Internship added successfully with ID: " + created.getId());
	}

	private static void viewAll() {
		System.out.println("\n--- All Internships ---");
		printList(manager.getAll());
	}

	private static void searchMenu() {
		System.out.println("\n--- Search ---");
		System.out.println("1. By company name");
		System.out.println("2. By status");
		System.out.print("Choose: ");
		String option = sc.nextLine().trim();
		if (option.equals("1")) {
			String keyword = prompt("Enter company keyword: ");
			printList(manager.searchByCompany(keyword));
		} else if (option.equals("2")) {
			String status = promptStatus();
			printList(manager.searchByStatus(status));
		} else {
			System.out.println("Invalid option.");
		}
	}

	private static void updateStatus() {
		int id = promptInt("Enter internship ID to update status: ");
		String newStatus = promptStatus();
		boolean success = manager.updateStatus(id, newStatus);
		System.out.println(success ? "Status updated." : "No internship found with that ID.");
	}

	private static void updateFull() {
		int id = promptInt("Enter internship ID to update: ");
		if (manager.findById(id) == null) {
			System.out.println("No internship found with that ID.");
			return;
		}
		System.out.println("Leave a field blank to keep its current value.");
		String company = prompt("Company name: ");
		String role = prompt("Role/Position: ");
		String duration = prompt("Duration: ");
		String stipendStr = prompt("Stipend: ");
		double stipend = stipendStr.isEmpty() ? -1 : Double.parseDouble(stipendStr);
		String status = prompt("Status: ");
		String date = prompt("Application date: ");
		String email = prompt("Contact email: ");
		String notes = prompt("Notes: ");

		boolean success = manager.updateInternship(id, company, role, duration, stipend, status, date, email, notes);
		System.out.println(success ? "Record updated." : "Update failed.");
	}

	private static void deleteInternship() {
		int id = promptInt("Enter internship ID to delete: ");
		boolean success = manager.deleteById(id);
		System.out.println(success ? "Internship deleted." : "No internship found with that ID.");
	}

	private static void sortMenu() {
		System.out.println("\n--- Sort ---");
		System.out.println("1. By stipend (highest first)");
		System.out.println("2. By company name (A-Z)");
		System.out.print("Choose: ");
		String option = sc.nextLine().trim();
		if (option.equals("1")) {
			printList(manager.sortByStipendDescending());
		} else if (option.equals("2")) {
			printList(manager.sortByCompanyName());
		} else {
			System.out.println("Invalid option.");
		}
	}

	private static void showStatistics() {
		System.out.println("\n--- Statistics ---");
		System.out.println(manager.getStatistics());
	}

	// ---------- Helper methods ----------

	private static void printList(List<Internship> list) {
		if (list.isEmpty()) {
			System.out.println("No records to display.");
			return;
		}
		for (Internship i : list) {
			System.out.println(i);
		}
		System.out.println("(" + list.size() + " record(s))");
	}

	private static String prompt(String message) {
		System.out.print(message);
		return sc.nextLine().trim();
	}

	private static int promptInt(String message) {
		while (true) {
			System.out.print(message);
			String input = sc.nextLine().trim();
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid whole number.");
			}
		}
	}

	private static double promptDouble(String message) {
		while (true) {
			System.out.print(message);
			String input = sc.nextLine().trim();
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}

	private static String promptStatus() {
		System.out.println("Status options: Applied / Interview / Offered / Rejected / Completed");
		return prompt("Enter status: ");
	}
}
