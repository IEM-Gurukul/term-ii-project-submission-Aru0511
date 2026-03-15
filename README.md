[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title

Smart Email Filtering System using Strategy Pattern

## Problem Statement (max 150 words)

Modern email inboxes receive many messages including work emails, spam, and promotional messages. Manually organizing these emails is inefficient and important messages may be missed. This project implements a Smart Email Filtering System that automatically categorizes emails into folders such as Spam, Work, Important, and Personal using different filtering strategies.

## Target User

Students, professionals, and users who want their emails automatically organized.

## Core Features

- Accept email input (sender, subject, content)
- Automatically classify emails into folders
- Use Strategy Pattern for flexible filtering rules
- Store categorized emails using collections
- Provide a GUI interface using Java Swing


## OOP Concepts Used

- Abstraction:FilterStrategy interface defines common filtering behavior
- Inheritance:SpamFilterStrategy, WorkFilterStrategy, ImportantFilterStrategy classes implement the interface
- Polymorphism:Different filtering strategies are applied dynamically
- Exception Handling:Prevents crashes due to invalid input
- Collections / Threads:ArrayList is used to store categorized emails


## Proposed Architecture Description

The system consists of an Email entity class, filtering strategies implementing the Strategy Pattern, and an EmailManager class responsible for applying filters and storing emails in appropriate folders. The GUI allows users to input emails and view categorized results.


## How to Run

1. Open the project folder in VS Code or any Java IDE.
2.Make sure Java JDK is installed on your system.
3.Open the terminal inside the project folder.
4.Compile all Java files using the command: javac *.java
5.Run the program using the command:java EmailUI
6.The Smart Email Filtering System GUI window will open.
7.Enter the sender, subject, and content of an email.
8.Click Add Email to process the email.
9.The system will automatically categorize the email into Spam, Work, Important, or Personal folders based on the filtering rules.

## Git Discipline Notes
Minimum 10 meaningful commits required.
