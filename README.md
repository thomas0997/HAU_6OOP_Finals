# Campus Navigation App

A CLI-based Java application built for the 6OOP course at Holy Angel University. It simulates campus navigation and information services using core Object-Oriented Programming principles: encapsulation, inheritance, polymorphism, exception handling, and arrays of objects.

**Team:** David, Francis Nathan · Dayrit, Sean Deniel · Franco, Thomas · Rodriguez, Daniel Rein · Satumbaga, Hans Drew
**Section:** CYB-201

---

## What It Does

- View all campus locations (buildings, events, facilities), loaded from a CSV file
- Log in as a Faculty, Student, or Visitor, each with different access to location data
- Plan a route between two locations and calculate straight-line distance
- View the campus map as an image
- Persist student records between program runs

Locations are placed on a simulated Cartesian plane (not real GPS), and route distance uses the standard two-point distance formula from Analytic Geometry.

---

## Project Structure

```
├── Data
│   ├── CampusData.csv       (campus location data — buildings, events, facilities)
│   ├── Records.csv          (persisted student records)
│   └── UpdatedCampusMap.png (image shown by showMap())
├── src
│   ├── Locations
│   │   ├── CampusEntity.java      (abstract base class)
│   │   ├── Navigable.java         (interface)
│   │   ├── Building.java
│   │   ├── AcademicBuilding.java
│   │   ├── AdminBuilding.java
│   │   ├── Event.java
│   │   └── Facility.java
│   ├── Users
│   │   ├── User.java              (abstract base class)
│   │   ├── Student.java
│   │   ├── Faculty.java
│   │   └── Visitor.java
│   ├── Support
│   │   ├── Route.java
│   │   ├── DataManager.java
│   │   ├── Messages.java
│   │   ├── NavigationException.java
│   │   └── LocationNotFoundException.java
│   └── Root
│       └── Main.java
├── LICENSE
└── README.md
```

---

## Class Reference

### Location System

| Class | Fields | Notes |
|---|---|---|
| `CampusEntity` (abstract) | `id, name, description, x, y` | Base class for every physical location. Implements `Navigable`. Declares abstract `getInfo()`. |
| `Navigable` (interface) | — | Requires `getDirections(): String`. |
| `Building` | `floorCount, facilities[]` | Extends `CampusEntity`. |
| `AcademicBuilding` | `coursesOffered[]` | Extends `Building` — multi-level inheritance. |
| `AdminBuilding` | `officeHours` | Extends `Building` — multi-level inheritance. |
| `Event` | `dateTime, organizer` | Extends `CampusEntity`. |
| `Facility` | `facilityType` | Extends `CampusEntity`. |

### User System

| Class | Fields | Notes |
|---|---|---|
| `User` (abstract) | `userId` (auto-generated), `userType` | Declares abstract `navigate()` and `authenticate()`. |
| `Student` | `studentId, program` | `authenticate()` always returns true. `navigate()` shows all locations except `AdminBuilding`. |
| `Faculty` | `password, facultyID` | `authenticate()` checks a hardcoded password. `navigate()` shows every location, unfiltered. |
| `Visitor` | `purposeOfVisit, typeOfID` | `authenticate()` always returns true. `navigate()` shows only `Facility` and `Event` locations. |

### Support System

| Class | Purpose |
|---|---|
| `Route` | Holds a `CampusEntity[] waypoints` array. `calculateRoute()` computes total straight-line distance; throws `LocationNotFoundException` if fewer than 2 waypoints. |
| `DataManager` | Only class allowed to touch the CSV files. `saveStudents()`/`loadStudents()` handle `Records.csv`. `loadCampusData()` reads `CampusData.csv` and rebuilds the correct subclass per row. |
| `NavigationException` | Custom exception for navigation, authentication, and file errors. |
| `LocationNotFoundException` | Extends `NavigationException`. Thrown when a searched location doesn't exist. |
| `Messages` | Static `msg[]` array of predefined error strings, shared across the program. |
| `Main` | Entry point. Forces login before showing the menu, then loops on: view map, view locations (role-filtered), plan a route, exit. |

---

## How Access Differs by Role

| Location Type | Visitor | Student | Faculty |
|---|---|---|---|
| Facility | ✅ | ✅ | ✅ |
| Event | ✅ | ✅ | ✅ |
| Building / AcademicBuilding | ❌ | ✅ | ✅ |
| AdminBuilding | ❌ | ❌ | ✅ |

---

## Data Files

**`CampusData.csv`** — one row per location, in the format:
```
type,id,name,description,x,y,extra1,extra2,facilities
```
`type` is one of `Admin`, `Academic`, `Building`, `Facility`, `Event`, and determines which subclass `DataManager.loadCampusData()` builds. Lists (`coursesOffered`, `facilities`) are semicolon-separated within their cell.

**`Records.csv`** — one row per saved student, format: `studentId,program`. Written automatically on Student login; not manually edited.

---

## Compiling and Running

From the project root:

```bash
javac -d bin src/Root/Main.java src/Users/*.java src/Support/*.java src/Locations/*.java
java -cp bin Root.Main
```

Run from the project root specifically — `Main` reads `Data/CampusData.csv`, `Data/Records.csv`, and `Data/UpdatedCampusMap.png` using relative paths.

---

## Scope

- Storing and managing user records for students, faculty, and visitors
- Calculating shortest straight-line distance between two points
- Viewing all registered campus locations, events, and facility details
- Differentiating access per user role
- Login/authentication with a hardcoded password check for faculty
- Loading campus location data from CSV, so new locations can be added without recompiling
- Displaying the campus map as an image
- Custom exception handling for invalid input and missing data

## Limitations

- No real-time GPS or live location tracking
- No graphical map rendered within the program — map is a static external image
- No pathfinding algorithm — distance is a straight line, not a walkable route
- No live/dynamic event updates
- Coordinates are simulated on an imaginary Cartesian plane
- No real database — both CSVs are flat files, no query support, no concurrency protection
- No real authentication security — credentials are hardcoded, not encrypted
- Map image can't be closed programmatically once opened
- Single-user operation only

---

## Build Order (for reference)

1. `CampusEntity`, `Navigable`
2. `Building` → `AcademicBuilding`, `AdminBuilding` · `Event`, `Facility` · `User` → `Student`, `Faculty`, `Visitor` · `NavigationException`, `LocationNotFoundException`, `Messages` (all independent of each other)
3. `Route`, `DataManager`
4. `Main`