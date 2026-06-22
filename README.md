# Amazon Automation Testing Framework

Automation testing framework for Amazon website using Java, Selenium WebDriver, TestNG, and Maven.

## Tech Stack
- Java 21
- Selenium WebDriver 4.x
- TestNG 7.x
- Maven
- Page Object Model (POM)

## Features
- Full Search test class execution (`tests.Search.Search`)
- Search product functionality (TC_SF_001 to TC_SF_022)
- Automated Page Object interaction
- Wait utilities for synchronization
- Allure Report integration for detailed test execution tracking
- GitHub Actions CI/CD runs the full Search test class and deploys the Allure HTML report

## Reporting
The project uses **Allure Report** (not Extent Report) to provide detailed execution steps, including:
- Test steps with @Step annotation
- Assertion status
- Execution time and environment details
- HTML report deployment through GitHub Pages after CI execution

## Project Structure
- `src/test/java/tests/Search/Search.java`: Contains the full Search test class from TC_SF_001 to TC_SF_022.

## How to run locally
1. Clone the repository
2. Ensure you have Maven and JDK 21 installed
3. Run the full Search test class: `mvn clean -Dtest=tests.Search.Search test`
4. Generate report: `mvn allure:serve`
