# Library Management System 

A Java console application for managing a library system with user roles, book management, and borrowing functionality. This project is containerized using Docker and Docker Compose.

## Architecture

The application consists of three containers:
- **Java Application**: Console-based library management system
- **MySQL Database**: Stores books, users, and borrowing data
- **Adminer**: Web-based database administration interface

##  Prerequisites

- Docker
- Docker Compose

##  Quick Start

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd library-ejada
   ```

2. **Set up environment variables:**
   ```
   create .env file with default values below
   ```
   
3. **Build and run the application:**
   ```bash
   docker-compose up --build
   ```

4. **Access the application:**
   - **Console Application**: The Java app will start in interactive mode
   - **Adminer (Database Admin)**: Open http://localhost:8080 in your browser
     - System: MySQL
     - Server: db
     - Username: library_user
     - Password: 510124
     - Database: library_db

##  Environment Variables

| Variable | Description | Default      |
|----------|-------------|--------------|
| `DB_HOST` | Database host | db           |
| `DB_PORT` | Database port | 3306         |
| `DB_NAME` | Database name | library_db   |
| `DB_USER` | Database user | library_user |
| `DB_PASSWORD` | Database password | 510124       |
| `DB_ROOT_PASSWORD` | MySQL root password | 510124       |

## Usage

### Running the Application

Since this is a **console application** that requires interactive input, we run it differently than web applications:

1. **Database and Adminer** run in the background (detached mode)
2. **Java Application** runs interactively for console input

**To stop everything:**
```bash
docker-compose down
```

**To rebuild and restart:**
```bash
docker-compose down
docker-compose up db adminer -d
docker-compose run --rm app
```

### Console Application Features

**Admin Functions:**
- Add, edit, and delete books
- Register new users (admin or regular)
- View all books and genres

**Regular User Functions:**
- View book catalog
- Borrow and return books
- View borrowed books
- Browse genres

### Database Administration

Access Adminer at http://localhost:8080 to:
- View and edit database tables
- Execute SQL queries
- Monitor database structure

## Troubleshooting

### Common Issues

1. **Port conflicts:**
   - Ensure ports 3307 and 8080 are available
   - Change ports in `.env` if needed

2. **Database connection issues:**
   - Wait for MySQL to fully start 
   - Check environment variables in `.env`

3. **Application not starting:**
   - Check Docker logs: `docker-compose logs app`
   - Ensure all containers are running: `docker-compose ps`

