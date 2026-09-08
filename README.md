# Java File Build Order — Campus Navigation App

## Stage 1 — Build First (everything else depends on this)

### `CampusEntity.java` — Satumbaga, Hans Drew
- Abstract class — cannot be instantiated directly
- Fields: `id`, `name`, `x`, `y`, `description` (all private)
- Constructor + getters/setters for all fields
- Abstract method: `getInfo()` — every subclass must define its own version
- Implements `Navigable` interface

### `Navigable.java` — Satumbaga, Hans Drew
- Interface with one method: `getDirections()`
- No implementation — just declares that any class using it must provide this method

---

## Stage 2 — Build After Stage 1 (can be done in parallel with each other)

### `Building.java` — Rodriguez, Daniel Rein
- Extends `CampusEntity`
- Adds: `floorCount` (int), `facilities[]` (String array)
- Overrides `getInfo()` to describe a building

### `AcademicBuilding.java` — Rodriguez, Daniel Rein
- Extends `Building` (multi-level inheritance)
- Adds: `coursesOffered[]`
- Overrides `getInfo()`

### `AdminBuilding.java` — Rodriguez, Daniel Rein
- Extends `Building`
- Adds: `officeHours`
- Overrides `getInfo()`

### `Event.java` — David, Francis Nathan
- Extends `CampusEntity`
- Adds: `dateTime`, `organizer`
- Overrides `getInfo()`

### `Facility.java` — David, Francis Nathan
- Extends `CampusEntity`
- Adds: `facilityType`
- Overrides `getInfo()`

---

## Stage 2 (Independent) — Can Be Built Anytime, No Dependency on Stage 1

### `User.java` — Dayrit, Sean Deniel
- Abstract class
- Fields: `userId`, `userType`
- Abstract methods: `navigate()`, `authenticate()`

### `Student.java` — Dayrit, Sean Deniel
- Extends `User`
- Adds: `studentId`, `course`
- Overrides `navigate()` and `authenticate()` (always returns true — no password needed)

### `Faculty.java` — Dayrit, Sean Deniel
- Extends `User`
- Adds: `password`, `facultyID`
- Overrides `authenticate()` — checks password against a hardcoded value

### `Visitor.java` — Dayrit, Sean Deniel
- Extends `User`
- Adds: `purposeOfVisit`, `typeOfID`
- Overrides `navigate()` and `authenticate()` (always true)

### `NavigationException.java` — Franco, Thomas
- Extends `Exception`
- Field: `errorCode`
- Custom exception used across the whole program
- No dependency on any other custom class — can be built anytime

### `LocationNotFoundException.java` — Franco, Thomas
- Extends `NavigationException`
- No new fields — inherits `errorCode`
- Used specifically when a location search fails

### `Messages.java` — Franco, Thomas
- Static String array holding all error messages, indexed by number
- Used by `NavigationException` and `DataManager` so error text isn't repeated everywhere
- No dependency on any other custom class — can be built anytime

---

## Stage 3 — Build After Stage 2 Is Done

### `Route.java` — Franco, Thomas
- Needs: `CampusEntity` (and its subclasses to test properly), `NavigationException`
- Fields: `waypoints[]` (array of `CampusEntity` — the required array of objects), `totalDistance`
- `calculateRoute()` — loops through waypoints, applies the distance formula, throws `NavigationException` if something's wrong

### `DataManager.java` — Franco, Thomas
- Needs: `Student`, `NavigationException`
- Field: `filePath`
- `saveStudents()` / `loadStudents()` — reads/writes `Student` data to `Records.csv`
- Only class allowed to touch the file directly

---

## Stage 4 — Build Last (needs everything above to compile)

### `Main.java` — Franco, Thomas
- Entry point: `main()`, `menu()`, `login()`, `showMap()`
- Creates the array of all `CampusEntity` objects (hardcoded from the building table)
- Ties every other class together into one working program

---

**Summary of order:**
1. Hans → `CampusEntity`, `Navigable`
2. Daniel + Francis (parallel) → Building hierarchy, Event/Facility — *and, independently, at any time* → Sean → User hierarchy, Thomas → Exceptions/Messages
3. Thomas → `Route`, `DataManager`
4. Thomas → `Main`