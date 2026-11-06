# 📦 StockMaster – Inventory & Billing Management System

StockMaster is a Java-based Inventory and Billing Management System designed to simplify product management, inventory tracking, and customer billing.

The application allows users to manage products, monitor stock levels, generate customer invoices, and track sales through a simple menu-driven interface.

This project is developed using Core Java concepts, Object-Oriented Programming, Collections, and File Handling.

---

## 🚀 Features

### 1. Dashboard
- View total number of products.
- Monitor total available stock units.
- Calculate total inventory value.
- View total sales revenue.
- Monitor low-stock products.

### 2. Product Management
- Add new products.
- View all available products.
- Search products by name or category.
- Update product information.
- Delete products.

### 3. Billing Management
- Enter customer details.
- Add multiple products to a bill.
- Automatically calculate the total amount.
- Generate invoices with unique invoice numbers.
- Automatically update inventory after successful billing.

### 4. Sales Management
- Maintain sales history.
- View customer billing details.
- Track invoice numbers and billing dates.
- Calculate total sales revenue.

### 5. Inventory Management
- Monitor available product quantities.
- Calculate individual product stock values.
- Identify low-stock products.
- Identify out-of-stock products.

### 6. Data Persistence
- Save product information automatically.
- Store customer billing records.
- Preserve sales history.
- Retrieve previously saved information when restarting the application.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core application development |
| OOP | Classes, objects and encapsulation |
| ArrayList | Managing products and sales |
| Collections | Storing application data |
| File Handling | Reading and writing files |
| Serialization | Saving and retrieving Java objects |
| Scanner | User input handling |

---

## 📂 Project Structure

```text
StockMaster/
│
├── Main.java
│
├── README.md
│
└── stockmaster-data/
    └── stockmaster.dat
```

**Main.java:** Contains the complete application source code, including product management, billing, inventory reports, and data storage.

**stockmaster.dat:** Automatically generated data file used to store products and sales records.

---

## ⚙️ Installation and Execution

### Prerequisites

- Java Development Kit (JDK 8 or later).
- Visual Studio Code or another Java-supported IDE.
- Java installed and configured on your system.

### Step 1: Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/StockMaster.git
```

### Step 2: Open the Project

Open the downloaded StockMaster folder in Visual Studio Code.

### Step 3: Compile the Java Program

Open the terminal and execute:

```bash
javac Main.java
```

### Step 4: Run the Application

```bash
java Main
```

The StockMaster application will start in the terminal.

---

## 📋 Application Menu

The application provides the following operations:

| Option | Operation |
|---|---|
| 1 | Dashboard |
| 2 | Add Product |
| 3 | View All Products |
| 4 | Search Product |
| 5 | Update Product |
| 6 | Delete Product |
| 7 | Generate Customer Bill |
| 8 | Sales History |
| 9 | Inventory Report |
| 10 | Exit |

---

## 💾 Data Storage

StockMaster uses Java Object Serialization to store application data.

The application automatically creates a folder named `stockmaster-data` and stores the information in `stockmaster.dat`.

The stored information includes:

- Product details.
- Product prices and quantities.
- Customer information.
- Invoice details.
- Sales history.

This allows information to remain available after closing and reopening the application, as long as the data file is preserved.

---

## 🎯 Project Objectives

- Understand practical implementation of Core Java.
- Apply Object-Oriented Programming principles.
- Implement product and inventory management.
- Develop a basic billing and invoice generation system.
- Understand Java File Handling and Serialization.
- Build a practical software project for real-world business operations.

---

## 🔮 Future Enhancements

The following improvements can be considered for future versions:

- Graphical User Interface using Java Swing.
- Database integration using MySQL.
- PDF invoice generation.
- Advanced sales analytics and graphical reports.
- User authentication and role-based access.
- Customer management module.
- Export inventory reports to CSV or Excel.

---

## 👩‍💻 Developer

**Tejaswini Yalam**

B.Tech – Computer Science and Engineering  
Specialization: Artificial Intelligence and Machine Learning

---

## 📌 Project Information

- **Project Name:** StockMaster
- **Project Type:** Inventory and Billing Management System
- **Programming Language:** Java
- **Application Type:** Console-Based Application
- **Version:** 2.0

---

⭐ If you find this project interesting, consider giving the repository a star!
