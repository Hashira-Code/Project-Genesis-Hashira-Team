# 📄 Hashira Code Review from The Kernels Team

This document provides a structured and comprehensive review of the project, covering architecture, code quality, package organization, and individual use cases.  
The goal is to highlight strengths and provide clear, actionable improvements in a unified and professional style.

---

# ⭐ Strengths
- **Excellent package separation** (`data`, `domain`, `mapper`) aligned with Clean Architecture principles.
- **Strong use of interfaces** in the mapper layer, showing good abstraction practices.
- **Effective use of generics** to reduce duplication and improve reusability.
- **Well‑structured models and source classes**, with parsing logic encapsulated in private functions.
- **Most use cases depend only on repository abstractions**, which is correct for the domain layer.

---

# 🏛️ Architecture Review
- Inconsistent use of `invoke()` vs `execute()`. A unified approach is recommended.
- Several use cases rely on `getAll()` even when more specific repository methods would be more efficient.
- Some files contain **magic numbers** or repeated strings; these should be extracted into `private const val`.
- Code style varies noticeably between team members, indicating a lack of internal code review.
- **Dead code detected**: some repository functions are defined but never used. These should be removed or integrated.

---

# 🧠 General Code Quality Notes
- Some variable names can be more descriptive for clarity.
- A few use cases perform multiple responsibilities inside `invoke()`, violating SRP.
- Some use cases return only `String` values instead of richer domain models.
- Consider using `asSequence()` for large collections to improve performance.
- Repository results should be stored in local variables instead of calling `getAll()` multiple times.

---

# 📌 Use Case Review 

### 1. Calculating Mentee Attendance Times Use Case
- Rename `attendance` → `attendanceAll`.
- Rename `AttendanceTime` → `attendanceTimes` (camelCase).
- `invoke()` contains multiple responsibilities; consider splitting logic.
- Prefer `getById()` over `getAll()` when possible.
- Store `menteeRepo.getAll()` in a local variable.

---

### 2. Calculate Attendance Percentage Use Case
- Ensure the use case remains decoupled from other features.
- Standardize naming and structure with the rest of the domain layer.

---

### 3. Evaluate Team Health Use Case
- Use `Mentee` directly instead of the full package path.
- Consider `asSequence()` for performance.
- Mapping logic could be moved to a dedicated mapper.

---

### 4. Find Lead Mentor for Mentee Use Case
- Use `operator fun invoke()` for proper Kotlin syntax.

---

### 5. Find Mentees Names With Task Use Case
- Store `menteeRepo.getAll()` in a variable.
- Ensure the use case is placed under `domain.usecase`.

---

### 6. Find Projects Assigned To Team Use Case
- Implementation meets current requirements.

---

### 7. Find Teams With No Project Use Case
- Prefer `getById()` over `getAll()` when applicable.

---

### 8. GetAbsentMenteesUseCase
- Rename `absentIds` → `absentMenteeIds`.
- Prefer `getByIds()` over `getAll()`.
- Consider `asSequence()` for large datasets.

---

### 9. GetAverageScorePerMenteeUseCase
- Rename `averageScorePerMentee` → `averageScoreByMenteeId`.
- Use `associateBy { it.id }` for faster lookups.
- Consider `asSequence()`.

---

### 10. GetMenteesOrderedByTaskCountUseCase
- Rename `taskCountPerMentee` → `taskCountByMenteeId`.
- Prefer `getByIds()` over `getAll()`.
- Consider `asSequence()`.

---

### 11. GetMenteesPerTeamUseCase
- Prefer `getByTeamIds()` over `getAll()`.
- Consider `asSequence()`.

---

### 12. GetMenteesWithMultipleSubmissionsUseCase
- Prefer `getByIds()` over `getAll()`.
- Consider `asSequence()`.

---

### 13. GetOverallPerformanceAverageForTeamUseCase
- Prefer `getByMenteeIds()` over `getAll()`.
- Consider making `invoke(teamId: String)` consistent with other use cases.

---

### 14. GetMenteesWithoutAnySubmissionUseCase
- Prefer `getByIds()` over `getAll()`.
- Consider `asSequence()`.

---

### 15. GetTeamsWithMenteesCountUseCase
- Returning `Pair<String, Int>` may cause ambiguity.
- Consider returning a richer model or including `teamId`.

---

### 16. GetPerformanceBreakdownForMenteeUseCase
- Rename `execute()` → `invoke()` for consistency.

---

### 17. MenteeWithPoorAttendanceUseCase
- Consider returning richer data instead of names only.

---

### 18. MenteesWithPerfectAttendanceUseCase
- Consider returning richer data instead of names only.

---

### 19. TeamAttendanceReportUseCase
- Using `team.name` as a map key may cause collisions.
- Prefer using `team.id` or returning the `Team` object.

---

### 20. TopScoringMenteeOverallUseCase
- Naming is clear; ensure consistency with other use cases (prefer `invoke()`).
- Consider returning a richer model instead of primitive values.
- Prefer targeted repository methods (`getById()`, `getByIds()`) over `getAll()`.
- `invoke()` can be simplified by delegating calculations to private functions.
- Use `asSequence()` for large collections.
- Depends only on abstractions; aligns with Clean Architecture.

---

# 🎯 Final Notes
- Unify naming conventions across the entire project.
- Adopt a shared style guide for consistent formatting.
- Perform internal code reviews before submission.
- Remove unused functions and avoid dead code.
- Replace magic numbers and strings with constants.
- Ensure all required features (e.g., testing 10 use cases) are implemented.
- Please also pay attention to maintaining clear branch naming and meaningful commit messages in future updates, ensuring a cleaner and more consistent workflow.
- Please also make sure to keep the README file updated so it always reflects the latest changes and improvements in the project.

---

# **💙 A Message from Team Heart to ByteBloom 💙**
# **💙💙💙->>> The Kernels <<<-💙💙💙**
From our team to yours, we genuinely wish you clarity, growth, and continuous improvement.  
May we keep learning, building, and rising together — side by side — inside the ByteBloom fortress. 💙

---


