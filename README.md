The SmartCart search module goes beyond exact keyword matching by leveraging Natural Language Processing to understand user queries and instantly surface both direct matches and contextually similar products.

How It Works:
Query Processing: When a user searches for a product, OpenNLP analyzes the query text, performing tokenization, lemmatization, and entity extraction to capture the true intent.
Event-Driven Pipeline: The search query and user intent metadata are published to an Apache Kafka topic, decoupling the search request from inventory processing to ensure high throughput and responsiveness.
Smart Matching & Similarity: The Spring Boot backend consumes the event, executing optimized SQL queries via Hibernate ORM to fetch both exact inventory matches and alternative/complementary items based on category and description vectors.
Responsive UI: The React.js frontend dynamically renders the results grid, cleanly separating direct matches from the "Similar Products You Might Like" recommendation carousel.

Tech Stack Highlights
Frontend: React.js (Component-based UI with responsive product grids)
Backend: Java, Spring Boot (RESTful APIs and service orchestration)
ORM: Hibernate / JPA (Database mapping and entity management)
Messaging: Apache Kafka (Asynchronous event streaming for search analytics and decoupled processing)
NLP: OpenNLP (Query tokenization, intent extraction, and text normalization)
Database: SQL (Relational data storage for products, categories, and inventory)

Project Structure:

SmartCart/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── ModelTrainer/
│   │   │   │   └── IntentDoccatTrainer.java
│   │   │   │
│   │   │   └── com/SmartCart/
│   │   │       ├── Cache/
│   │   │       ├── Config/
│   │   │       ├── Controller/
│   │   │       ├── DTO/
│   │   │       ├── Entity/
│   │   │       ├── Repository/
│   │   │       ├── Service/
│   │   │       └── SmartCartApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql
│   │       ├── schema.sql
│   │       ├── sample_queries.sql
│   │       └── nlp/
│   │           ├── intent-doccat.bin
│   │           └── intent-doccat.train
│   │
│   └── test/
│       └── java/
│           └── com/SmartCart/
│               └── Service/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
