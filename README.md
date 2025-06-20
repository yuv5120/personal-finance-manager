# 💰 Personal Finance Manager

**A comprehensive Spring Boot application for managing your personal finances with ease.**

---

## 🌟 About This Project

This Personal Finance Manager is a robust web-based application built to help you take control of your financial life. Whether you're tracking daily expenses, managing income streams, or working toward savings goals, this system provides all the tools you need in one secure, user-friendly platform.

**Built as a complete backend system with RESTful APIs**, this application demonstrates enterprise-level architecture and best practices in modern Java development.

## ✨ Key Features

### 🔐 **Secure User Management**
- **Email-based Registration**: Simple signup with email, password, full name, and phone
- **Session-based Authentication**: Secure login system with proper session management
- **Data Isolation**: Your financial data stays completely private and separate from other users
- **Secure Logout**: Proper session invalidation for security

### 💳 **Complete Transaction Management**
- **Full CRUD Operations**: Create, read, update, and delete financial transactions
- **Smart Categorization**: Pre-built categories plus custom category creation
- **Flexible Filtering**: Filter transactions by date range, category, and type
- **Detailed Tracking**: Amount, date, category, and description for each transaction

### 📊 **Category System**
- **Default Categories**: 
  - **Income**: Salary
  - **Expenses**: Food, Rent, Transportation, Entertainment, Healthcare, Utilities
- **Custom Categories**: Create your own income and expense categories
- **Smart Validation**: Categories in use cannot be deleted to maintain data integrity

### 🎯 **Savings Goals**
- **Goal Setting**: Set target amounts and dates for your financial goals
- **Progress Tracking**: Automatic calculation of progress based on your transactions
- **Visual Progress**: See percentage completion and remaining amounts
- **Flexible Management**: Update or delete goals as your priorities change

### 📈 **Comprehensive Reports**
- **Monthly Reports**: Detailed breakdown of income and expenses by category
- **Yearly Reports**: Annual financial overview with aggregated data
- **Net Savings Calculation**: Automatic calculation of your financial progress

## 🚀 Quick Start

### Prerequisites
Make sure you have the following installed:
- **Java 17+** (JDK)
- **Maven** or **Gradle** (for building)
- **Git** (for cloning the repository)

### Installation & Setup

```bash
# Clone the repository
git clone https://github.com/yuv5120/personal-finance-manager.git

# Navigate to the project directory
cd personal-finance-manager

# Build the project (Maven)
mvn clean install

# Or if using Gradle
./gradlew build

# Run the application
mvn spring-boot:run

# Or with Gradle
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 🌐 Live Demo
The application is deployed and accessible at: **[Your Deployed URL Here]**

You can test all endpoints using the provided test script:
```bash
bash financial_manager_tests.sh https://your-deployed-url.com/api
```

## 🛠️ Technical Architecture

### Technology Stack
| Component | Technology |
|-----------|------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security |
| **Database** | H2 (In-memory) |
| **Testing** | JUnit 5 + Mockito |
| **Build Tool** | Maven/Gradle |
| **Documentation** | JavaDoc + OpenAPI |



## 🚀 Deployment

### Local Development
```bash
# Using Maven
mvn spring-boot:run

# Using Gradle  
./gradlew bootRun

# Using Docker (if Dockerfile is present)
docker build -t personal-finance-manager .
docker run -p 8080:8080 personal-finance-manager
```
