# Internship Tracker (Java Console Mini Project)

A simple menu-driven, console-based Java application to track internship
applications — company, role, stipend, status, etc. — with data saved to a
text file so it persists between runs.

## Features
- Add a new internship record
- View all records
- Search by company name or by status
- Update just the status, or edit a full record
- Delete a record
- Sort by stipend (highest first) or company name (A–Z)
- View statistics (counts per status, average/total stipend)
- Data is automatically saved to `data/internships.txt` after every change,
  and reloaded automatically the next time you run the program

## Project Structure
```
InternshipTracker/
├── .project                 # Eclipse project file
├── .classpath               # Eclipse build path
├── src/
│   └── com/internshiptracker/
│       ├── Main.java              # Console menu / program entry point
│       ├── Internship.java        # Model class for one internship record
│       ├── InternshipManager.java # CRUD, search, sort, statistics logic
│       └── FileHandler.java       # Save/load records to/from a text file
└── data/                     # Created automatically at runtime to store data
```

## How to import into Eclipse
1. Unzip this folder anywhere on your computer.
2. Open Eclipse → **File → Import... → General → Existing Projects into Workspace**.
3. Click **Next**, then **Browse** and select the unzipped `InternshipTracker` folder.
4. Make sure the project is checked, then click **Finish**.
5. In the Package Explorer, expand `InternshipTracker → src → com.internshiptracker`.
6. Right-click `Main.java` → **Run As → Java Application**.
7. Use the on-screen menu in the Console view to interact with the program.

## Notes
- Requires Java 8 or later (any modern JDK).
- No external libraries are needed — everything uses core Java (`java.util`, `java.io`).
- The `data` folder and `internships.txt` file are created automatically the
  first time you add a record, so you don't need to create them manually.
