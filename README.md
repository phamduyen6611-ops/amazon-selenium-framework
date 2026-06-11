# Amazon Automation Testing Framework

Automation testing framework for Amazon website using Java, Selenium WebDriver, TestNG, and Maven.

## Tech Stack
- Java 21
- Selenium WebDriver 4.x
- TestNG 7.x
- Maven
- Page Object Model (POM)

## Features
- Search product functionality (TC_SF_001 to TC_SF_015)
- Automated Page Object interaction
- Wait utilities for synchronization
- Allure Report integration for detailed test execution tracking
- GitHub Actions CI/CD for automated testing and report deployment

## Reporting
The project uses **Allure Report** to provide detailed execution steps, including:
- Test steps with @Step annotation
- Assertion status
- Execution time and environment details

## How to run locally
1. Clone the repository
2. Ensure you have Maven and JDK 21 installed
3. Run tests: `mvn clean test`
4. Generate report: `mvn allure:serve`