# JavaFX Account Manager

## Overview
This JavaFX application acts as an account manager, carrying out fundamental CRUD operations for both customers and their bank accounts. It adheres to Object-Oriented Programming (OOP) principles and employs the Singleton pattern for managing application state. Additionally, it follows SOLID principles for robust and modular design.

## Features

- Create and manage customer accounts.
- Add bank accounts for customers.
- Deposit and withdraw funds from customer bank accounts.

## Design Patterns

**Singleton Pattern:**
The application employs the Singleton pattern to ensure a single point of control for managing the application state.

## Principles

**SOLID Principles:**
The application adheres to SOLID principles to enhance maintainability and extensibility.

## How to Run

### Requirements

- Java 8 or higher
 which can be downloaded here 
 https://openjfx.io/

- Apache Maven
https://maven.apache.org/download.cgi?.=

### Instructions to ensure the successful execution of this code
### First thing is to clone  the the repository 
1. Clone the repository:

### The command can be executed either in the Command Prompt on Windows or in Bash on a Mac. 

   ```command 
   git clone https://github.com/uzzy007/javafx-account-manager.git
   ```

2. Navigae to the project directory:

   ```command
   cd javafx-account-manager
   ```

3. Build the project with maven:

   ```command 
    mvn clean install
   ```

4. Run the application:

   ```command
    mvn clean javafx:run
   ```

## Usage

### Create a Customer

1. Click the **Create Customer** button.
2. Enter the customer's first name, last name, and email address.
3. Click the **Save** button.

### Create a Bank Account

1. Select a customer from the **Customers** table.
2. Click the **Create Bank Account** button.
3. Enter the bank account's name and balance.
4. Click the **Save** button.

### Deposit Funds

1. Select a bank account from the **Bank Accounts** table.
2. Enter the amount to deposit.
3. Click the **Deposit** button.

### Withdraw Funds

1. Select a bank account from the **Bank Accounts** table.
2. Enter the amount to withdraw.
3. Click the **Withdraw** button.
