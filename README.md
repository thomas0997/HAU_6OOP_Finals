# Java File Rundown — Campus Navigation App

## 1. `CampusEntity.java` — Hans
- Abstract class — cannot be instantiated directly
- Fields: `id`, `name`, `x`, `y`, `description` (all private)
- Constructor + getters/setters for all fields
- Abstract method: `getInfo()` — every subclass must define its own version
- Implements `Navigable` interface

## 2. `Navigable.java` — Hans
- Interface with one method: `getDirections()`
- No implementation — just declares that any class using it must provide this method

## 3. `Building.java` — Daniel
- Extends `CampusEntity`
- Adds: `floorCount` (int), `facilities[]` (String array)
- Overrides `getInfo()` to describe a building

## 4. `AcademicBuilding.java` — Daniel
- Extends `Building` (multi-level inheritance)
- Adds: `coursesOffered[]`
- Overrides `getInfo()`

## 5. `AdminBuilding.java` — Daniel
- Extends `Building`
- Adds: `officeHours`
- Overrides `getInfo()`

## 6. `Event.java` — Francis
- Extends `CampusEntity`
- Adds: `dateTime`, `organizer`
- Overrides `getInfo()`

## 7. `Facility.java` — Francis
- Extends `CampusEntity`
- Adds: `facilityType`
- Overrides `getInfo()`

## 8. `User.java` — Sean
- Abstract class
- Fields: `userId`, `userType`
- Abstract methods: `navigate()`, `authenticate()`

## 9. `Student.java` — Sean
- Extends `User`
- Adds: `studentId`, `course`
- Overrides `navigate()` and `authenticate()` (always returns true — no password needed)

## 10. `Faculty.java` — Sean
- Extends `User`
- Adds: `password`, `facultyID`
- Overrides `authenticate()` — checks password against a hardcoded value

## 11. `Visitor.java` — Sean
- Extends `User`
- Adds: `purposeOfVisit`, `typeOfID`
- Overrides `navigate()` and `authenticate()` (always true)

## 12. `Route.java` — Thomas
- Fields: `waypoints[]` (array of `CampusEntity` — the required array of objects), `totalDistance`
- `calculateRoute()` — loops through waypoints, applies the distance formula, throws `NavigationException` if something's wrong

## 13. `NavigationException.java` — Thomas
- Extends `Exception`
- Field: `errorCode`
- Custom exception used across the whole program

## 14. `LocationNotFoundException.java` — Thomas
- Extends `NavigationException`
- No new fields — inherits `errorCode`
- Used specifically when a location search fails

## 15. `DataManager.java` — Thomas
- Field: `filePath`
- `saveStudents()` / `loadStudents()` — reads/writes `Student` data to `Records.csv`
- Only class allowed to touch the file directly

## 16. `Messages.java` — Thomas
- Static String array holding all error messages, indexed by number
- Used by `NavigationException` and `DataManager` so error text isn't repeated everywhere

## 17. `Main.java` — Thomas (integration)
- Entry point: `main()`, `menu()`, `login()`, `showMap()`
- Creates the array of all `CampusEntity` objects (hardcoded from the building table)
- Ties everyone else's classes together into one working program

---

**Notes for distribution:** everyone fills in the fields/methods for their assigned file(s) exactly as listed — no need to understand the whole program, just their piece. Thomas is the only one who needs the full picture, since `Main` connects everything at the end.