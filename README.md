<div align="center">

# 📱 Smart Contact Manager

**Full-stack Spring Boot contact management system with Spring Security authentication, file uploads, and pagination**

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-orange.svg)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-purple.svg)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-green.svg)](https://www.thymeleaf.org/)
[![Maven](https://img.shields.io/badge/Maven-orange.svg)](https://maven.apache.org/)

</div>

## ✨ **Features**

- **🔐 Secure Authentication** - Spring Security with custom login, role-based access (USER/ADMIN)
- **👥 Multi-User Support** - Each user manages their own contacts
- **📝 Full CRUD Operations** - Add, edit, delete, view contacts with image uploads
- **📄 Pagination** - 5 contacts per page for better UX
- **🖼️ File Uploads** - Profile pictures for users & contacts (10MB limit)
- **✅ Input Validation** - Comprehensive Bean Validation + custom patterns
- **📱 Responsive UI** - Bootstrap + Thymeleaf templates
- **⚠️ Error Handling** - Global exception handling with user-friendly messages

## 🏗️ **Tech Stack**



Backend: Spring Boot 4.0.1 | Spring Security | Spring Data JPA | Hibernate
Frontend: Thymeleaf | Bootstrap | Custom CSS/JS
Database: MySQL | HikariCP connection pool
Security: BCrypt Password Encoder | CSRF Protection
Validation: Jakarta Bean Validation | Custom regex patterns
File Handling: Multipart uploads to /static/images/


## 🚀 **Quick Start**

### Prerequisites
- Java 21+
- MySQL 8.0 (Database: `smartcontactmanager`)
- Maven 3.9+

### 1. Clone & Setup

git clone <your-repo-url>
cd Smart_Contact_Manager

2. Database Setup
CREATE DATABASE smartcontactmanager;

Update application.yml:
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smartcontactmanager
    username: root
    password: root

3. Run Application
mvn spring-boot:run

Server runs on: http://localhost:8081

🌐 Demo Flow
1. http://localhost:8081/signup → Register new user
2. http://localhost:8081/signin → Login
3. http://localhost:8081/users/dashboard → User Dashboard
4. http://localhost:8081/users/addContact → Add Contact + Upload Image
5. http://localhost:8081/users/viewContacts/0 → Paginated Contacts


📋 Key Pages & Features
Page	URL	Features
Home	/	Landing page
Signup	/signup	User registration with validation
Login	/signin	Spring Security custom login
Dashboard	/users/dashboard	User profile + stats
Add Contact	/users/addContact	Form + image upload
View Contacts	/users/viewContacts/{page}	Pagination (5/page)
Edit Contact	/users/editContact/{id}	Update with image replace


🛡️ Security Features
✅ Custom Spring Security Configuration
✅ Role-based access (USER/ADMIN)
✅ BCrypt password encoding
✅ CSRF protection enabled
✅ File upload security (10MB limit)
✅ Ownership validation (can't edit others' contacts)
✅ Session management with JSESSIONID

🗄️ Database Schema
USER (1) ────── (M) CONTACT
├── id (PK)
├── name             ├── id (PK)
├── email (unique)   ├── firstName
├── password         ├── lastName
├── userRole         ├── phone (10 digits)
└── contacts         ├── email
                      ├── work
                      └── user_id (FK)


📁 Project Structure

src/main/java/com/smart/
├── controllers/     # MVC Controllers (UserController, SmartContactController)
├── entities/        # JPA Entities (User, Contact) + Enums
├── services/        # Business Logic + CustomUserDetails
├── repositories/    # JPA Repositories
├── configs/         # Spring Security Config
├── exceptions/      # Global Exception Handling
└── helper/          # Message & Session utilities

src/main/resources/
├── static/          # CSS/JS/Images
├── templates/       # Thymeleaf views
└── application.yml  # Configuration


🎯 Advanced Features
Image Upload Pipeline: MultipartFile → /static/images/ with overwrite protection

Pagination: Spring Data Pageable with 5 contacts/page

Ownership Security: Users can only CRUD their own contacts

File Validation: Phone (10 digits), Email regex, size constraints

Responsive Design: Bootstrap grid + custom styling

Flash Messages: Success/error feedback via HttpSession

🧪 Sample Usage
# 1. Register User
# Visit: http://localhost:8081/signup

# 2. Login
# Visit: http://localhost:8081/signin

# 3. Add Contact (via UI form)
# POST /users/processContact
# Fields: firstName, lastName, phone(10digits), email, work, image

# 4. View Paginated Contacts
# GET /users/viewContacts/0 (page 0)
# GET /users/viewContacts/1 (page 1)


🔮 Future Enhancements
REST API endpoints (JSON responses)

Search & filter contacts

Contact categories/tags

Export to CSV/Excel

Email verification

Admin dashboard

📞 Contact
Akash Gajendra Khabale
LinkedIn | Email | Portfolio

<div align="center"> Built with ❤️ using Spring Boot & Thymeleaf | #SpringBoot #SpringSecurity #Java #FullStack </div> ```
Copy-paste this entire README.md into your Smart_Contact_Manager repo root. Professional, feature-complete, and perfect for your portfolio! 🚀

