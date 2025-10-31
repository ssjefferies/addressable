# Addressable

A Spring Boot application for parsing, storing, and analyzing address data from delimited text files. The application automatically detects delimiters (comma, pipe, or tab), parses street addresses into components, and provides REST APIs for querying and reporting on the parsed data.

## Features

- **Automatic Delimiter Detection**: Handles comma-separated (CSV), pipe-delimited, and tab-delimited address files
- **Smart Address Parsing**: Extracts street number, street name, and unit information from full street addresses using regex patterns
- **SQLite Database**: Lightweight embedded database for storing parsed addresses
- **REST API**: Query addresses and generate various count-based reports
- **Error Logging**: Tracks parsing errors and file processing statistics
- **Structured Logging**: Pipe-delimited log format for easy parsing and analysis

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.7**
  - Spring Data JPA
  - Spring Web
  - Spring Boot DevTools
- **SQLite** with Hibernate Community Dialects
- **Maven** for build and dependency management
- **SLF4J** for logging

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Project Structure

```
addressable/
├── src/
│   ├── main/
│   │   ├── java/com/seanjefferies/addressable/
│   │   │   ├── controller/          # REST controllers
│   │   │   │   ├── AddressController.java
│   │   │   │   ├── FilesParsedController.java
│   │   │   │   └── LogController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── AddressDto
│   │   │   │   ├── AddressesByEntity
│   │   │   │   ├── FilesParsedDto
│   │   │   │   └── LogDto
│   │   │   ├── model/               # JPA entities
│   │   │   │   ├── Address.java
│   │   │   │   └── FilesParsed.java
│   │   │   ├── repository/          # Data repositories
│   │   │   │   ├── AddressRepository
│   │   │   │   └── FilesParsedRepository
│   │   │   ├── service/             # Business logic
│   │   │   │   ├── AddressService.java
│   │   │   │   ├── FilesParsedService.java
│   │   │   │   └── LoggingService.java
│   │   │   └── util/                # Utility classes
│   │   │   │   ├── InitializeData.java
│   │   │   │   └── ParseAddressResult
│   │   │   └── AddressableApplication # Main file - launches the app
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data1.txt            # Sample data file
│   └── test/                        # Test files
├── logs/                            # Application logs
├── addresses.db                     # SQLite database
└── pom.xml
```

## Installation and Setup

1. **Clone or extract the project**

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run    # Unix/Mac
   mvnw.cmd spring-boot:run  # Windows
   ```

The application will start on port 8080 (default Spring Boot port).

## Data Format

The application processes address files with the following format:
```
Street Address | City | State | ZIP | [Optional Field]
```

Supported delimiters:
- **Comma** (`,`): `123 Main St, New York, NY, 10001`
- **Pipe** (`|`): `123 Main St|New York|NY|10001`
- **Tab** (`\t`): `123 Main St	New York	NY	10001`

### Sample Data
```
181 Hobbiton Hatch Aft Capsule 970M|Tatooine|GL|33024|
5239 Port Krypton Node Aft Chamber 967Z,Rivendell,GT,52487,Orb of prophecy
7576 Starboard Coruscant Tube Forward Pod 390S	Krypton	AE	92214	Lightsaber
```

## How It Works

1. **Application Startup**: When the application launches, the `InitializeData` component runs automatically
2. **File Processing**: Reads `data1.txt` from the classpath and processes each line
3. **Address Parsing**: 
   - Detects the delimiter (comma, pipe, or tab)
   - Splits each line into address components
   - Uses regex pattern `^(\d*\s*)(\D+)(\d.*)$` to extract street number, name, and unit
4. **Database Storage**: Saves parsed addresses to the SQLite database
5. **Statistics Tracking**: Records parsing statistics including delimiter counts and error rates

## REST API Endpoints

### Address Endpoints

**Get all addresses**
```
GET /address/list
```
Returns all parsed addresses with full details.

```angular2html
[
    {
        "id": 1,
        "fullStreet": "181 Hobbiton Hatch Aft Capsule 970M",
        "streetNumber": "181",
        "streetName": "Hobbiton Hatch Aft Capsule",
        "unit": "970M",
        "city": "Tatooine",
        "state": "GL",
        "zipcode": "33024"
    },
    {
        "id": 2,
        "fullStreet": "5239 Port Krypton Node Aft Chamber 967Z",
        "streetNumber": "5239",
        "streetName": "Port Krypton Node Aft Chamber",
        "unit": "967Z",
        "city": "Rivendell",
        "state": "GT",
        "zipcode": "52487"
    },
    {
        "id": 3,
        "fullStreet": "7576 Starboard Coruscant Tube Forward Pod 390S",
        "streetNumber": "7576",
        "streetName": "Starboard Coruscant Tube Forward Pod",
        "unit": "390S",
        "city": "Krypton",
        "state": "AE",
        "zipcode": "92214"
    }
]
```

**Count by street**
```
GET /address/count/street
```
Returns count of addresses grouped by street.
```angular2html
[
    {
        "count": 65,
        "type": "STREET",
        "name": null
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Obsidian Pasage Pod"
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Port Coruscant Hatch"
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Port Dragon Hatch Module"
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Port Hobbiton Pasage Forward Module"
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Starboard Minas Podway Aft Pod"
    },
    {
        "count": 4,
        "type": "STREET",
        "name": "Starboard Node Forward Pod"
    },
    {
        "count": 3,
        "type": "STREET",
        "name": "Coruscant Deck Forward Module"
    },
    {
        "count": 3,
        "type": "STREET",
        "name": "Coruscant Pasage Aft Module"
    }
]
```

**Count by city**
```
GET /address/count/city
```
Returns count of addresses grouped by city.
```angular2html
[
    {
        "count": 177,
        "type": "CITY",
        "name": ""
    },
    {
        "count": 169,
        "type": "CITY",
        "name": "Arrakis"
    },
    {
        "count": 178,
        "type": "CITY",
        "name": "Bespin"
    },
    {
        "count": 169,
        "type": "CITY",
        "name": "Coruscant"
    },
    {
        "count": 175,
        "type": "CITY",
        "name": "Dagobah"
    },
    {
        "count": 163,
        "type": "CITY",
        "name": "Endor"
    },
    {
        "count": 165,
        "type": "CITY",
        "name": "Gondor"
    }
]
```

**Count by state**
```
GET /address/count/state
```
Returns count of addresses grouped by state.
```angular2html
[
    {
        "count": 38,
        "type": "STATE",
        "name": ""
    },
    {
        "count": 63,
        "type": "STATE",
        "name": "AE"
    },
    {
        "count": 59,
        "type": "STATE",
        "name": "BF"
    },
    {
        "count": 67,
        "type": "STATE",
        "name": "BV"
    },
    {
        "count": 57,
        "type": "STATE",
        "name": "CK"
    },
    {
        "count": 65,
        "type": "STATE",
        "name": "CY"
    }
]
```

**Count by ZIP (first 2 digits)**
```
GET /address/count/zip2
```
Returns count of addresses grouped by the first 2 digits of ZIP code.
```angular2html
[
    {
        "count": 53,
        "type": "ZIP2",
        "name": ""
    },
    {
        "count": 24,
        "type": "ZIP2",
        "name": "10"
    },
    {
        "count": 23,
        "type": "ZIP2",
        "name": "11"
    },
    {
        "count": 34,
        "type": "ZIP2",
        "name": "12"
    }
]
```

### File Processing Endpoints

**Get file parsing reports**
```
GET /file/list
```
Returns statistics about parsed files including:
- File name
- Total lines parsed
- Lines with errors
- Delimiter usage breakdown (commas, pipes, tabs)
- Processing date/time

```angular2html
[
    {
        "id": 1,
        "fileName": "data1.txt",
        "dateParsed": "2025-10-30T21:56:06.544",
        "totalLinesParsed": 2557,
        "linesWithErrors": 0,
        "linesDelimitedWithCommas": 865,
        "linesDelimitedWithPipes": 833,
        "limesDelimitedWithTabs": 859
    },
    {
        "id": 2,
        "fileName": "data1.txt",
        "dateParsed": "2025-10-30T22:05:13.087",
        "totalLinesParsed": 2557,
        "linesWithErrors": 2,
        "linesDelimitedWithCommas": 865,
        "linesDelimitedWithPipes": 833,
        "limesDelimitedWithTabs": 859
    },
    {
        "id": 3,
        "fileName": "data1.txt",
        "dateParsed": "2025-10-30T22:09:19.333",
        "totalLinesParsed": 2557,
        "linesWithErrors": 2,
        "linesDelimitedWithCommas": 865,
        "linesDelimitedWithPipes": 833,
        "limesDelimitedWithTabs": 859
    },
    {
        "id": 4,
        "fileName": "data1.txt",
        "dateParsed": "2025-10-30T22:15:42.439",
        "totalLinesParsed": 2557,
        "linesWithErrors": 0,
        "linesDelimitedWithCommas": 865,
        "linesDelimitedWithPipes": 833,
        "limesDelimitedWithTabs": 859
    }
]
```
### Logging Endpoints

**Get error logs**
```
GET /logs/error
```
Returns all ERROR level log entries from the application log file.
```angular2html
[
    {
        "date": "2025-10-30 22:47:36",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:47:36]:  Unable to parse address from line: 8174 Port Minas Tube Aft Capsule 517,Rivendell,GT,,"
    },
    {
        "date": "2025-10-30 22:47:36",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:47:36]:  Unable to parse address from line: 6212 Port Tatooine Podway Module 967Z,Hogwarts,BV,,"
    },
    {
        "date": "2025-10-30 22:48:52",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:48:51]:  Unable to parse address from line: 8174 Port Minas Tube Aft Capsule 517,Rivendell,GT,,"
    },
    {
        "date": "2025-10-30 22:48:52",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:48:51]:  Unable to parse address from line: 6212 Port Tatooine Podway Module 967Z,Hogwarts,BV,,"
    },
    {
        "date": "2025-10-30 22:50:13",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:50:12]:  Unable to parse address from line: 8174 Port Minas Tube Aft Capsule 517,Rivendell,GT,,"
    },
    {
        "date": "2025-10-30 22:50:13",
        "level": "ERROR",
        "logger": "com.seanjefferies.addressable.util.InitializeData",
        "message": "[File: data1.txt; Date: 2025-10-30 22:50:12]:  Unable to parse address from line: 6212 Port Tatooine Podway Module 967Z,Hogwarts,BV,,"
    }
]
```

## Configuration

### Database Configuration
The application uses SQLite with the following settings in `application.properties`:

```properties
spring.datasource.url=jdbc:sqlite:addresses.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
```

### Logging Configuration
Logs are written to `logs/addressable.log` with pipe-delimited format:

```properties
logging.file.name=logs/addressable.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss}|%level|%logger|%msg%n
```

Log format: `timestamp|level|logger|message`

Example:
```
2025-10-30 14:23:45|INFO|c.s.addressable.AddressableApplication|Starting AddressableApplication
2025-10-30 14:23:46|ERROR|c.s.addressable.util.InitializeData|Unable to parse address from line: invalid data
```

## Address Model

The `Address` entity contains:
- `id`: Auto-generated primary key
- `street`: Full street address
- `streetNumber`: Parsed street number
- `streetName`: Parsed street name
- `streetUnit`: Parsed unit/apartment number
- `city`: City name
- `state`: State abbreviation
- `zip`: ZIP code

## Error Handling

- **Parsing Errors**: Lines that cannot be parsed are logged and skipped
- **Delimiter Detection**: If no valid delimiter is detected, the line is logged as an error
- **Statistics Tracking**: All parsing errors are counted and stored in the database

## Development

### Adding New Data Files

To process a different data file:

1. Place your file in `src/main/resources/`
2. Update the `fileName` variable in `InitializeData.java` (line 188):
   ```java
   String fileName = "your-file-name.txt";
   ```

### Modifying the Street Parsing Pattern

The regex pattern for parsing street addresses is defined in `InitializeData.java`:

```java
public static final String STREET_PATTERN = "^(\\d*\\s*)(\\D+)(\\d.*)$";
```

This pattern extracts:
- Group 1: Street number (digits + optional spaces)
- Group 2: Street name (non-digits)
- Group 3: Unit/apartment (digit followed by anything)

### Running Tests

```bash
mvn test
```

## Troubleshooting

**Database locked error**
- Ensure only one instance of the application is running
- Delete `addresses.db` and restart to recreate the database

**Data file not found**
- Verify the file is in `src/main/resources/`
- Check the filename in `InitializeData.java`

**Port 8080 already in use**
- Add to `application.properties`:
  ```properties
  server.port=8081
  ```

## Future Enhancements

- [ ] Support additional delimiter types
- [ ] REST endpoint to upload and process new files
- [ ] More sophisticated address validation
- [ ] Export functionality (CSV, JSON)
- [ ] Web UI for viewing and searching addresses
- [ ] Batch processing of multiple files
- [ ] Custom exception handling
- [ ] Additional address parsing patterns for international addresses

## License

This is a demo project for Spring Boot development.

## Author

Sean Jefferies

## Support

For questions or issues, please refer to the Spring Boot documentation:
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
