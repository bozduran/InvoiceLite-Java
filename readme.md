# InvoiceLite

**InvoiceLite** is a Java-based application for creating receipts, saving them as PDF files, and storing related data in a MySQL database.

The program supports barcode scanning using a scanner that inputs the barcode as keystrokes, allowing it to quickly search the database for matching products. If a match is found, the product is retrieved and added to the receipt.
##  Features

-  Create new receipts
-  Add new customers
-  View all saved receipts
-  Add new products
-  Manage existing products

## Technologies Used

- Java
- Swing
- Hibernate ORM
- Hibernate Validator
- Apache PDFBox
- Lombok
- Log4j2 (for logging)
- JUnit 5

## Database

- **MySQL**
- Hibernate handles schema generation and data persistence automatically.

## Setup 

> Requires Java 21+ and Maven MySql installed.
