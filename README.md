# **Project Genesis**

## **Description**
A Kotlin project to manage **mentees, teams, and performance data** from CSV files and build a **domain object graph** linking Teams → Mentees → PerformanceSubmissions.  
The project demonstrates **object-oriented modeling**, **composition**, and **data relationships**.

## **Project Structure**
- **CSV Files:**  
  - `mentees.csv`  
  - `teams.csv`  
  - `performance.csv`  

- **Models Package (`models`):**  
  - `MenteeRaw`  
  - `TeamRaw`  
  - `PerformanceRaw`  

- **Domain Package (`domain`):**  
  - `Mentee`  
  - `Team`  
  - `PerformanceSubmission`  
  - `DomainBuilder` – builds and links the objects into a graph  

- **CSV Parsers:**  
  - `CsvParsers.kt` – functions to parse CSV files into raw data objects  

- **Main.kt:**  
  - Runs the DomainBuilder  
  - Builds the domain graph  
  - Displays Teams and their associated Mentees  

## **How to Run**
1. Open **Main.kt** in IntelliJ or any Kotlin IDE.  
2. Click **Run**.  
3. The output will show the list of Teams with their Mentees.  
   *(Optionally, you can extend it to display PerformanceSubmissions for each Mentee.)*  

## **Team Members**
- Doaa Allaham  
- Alaa Mousa  
- Tasnim Abu Nada  
- Elham Hassan
