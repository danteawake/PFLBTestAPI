# 🚀 PFLB Test API

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.9-red?style=for-the-badge&logo=apachemaven)
![Selenide](https://img.shields.io/badge/Selenide-UI_Testing-43B02A?style=for-the-badge)
![RestAssured](https://img.shields.io/badge/RestAssured-API_Testing-16A085?style=for-the-badge)
![TestNG](https://img.shields.io/badge/TestNG-Testing-E67E22?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-JDBC-336791?style=for-the-badge&logo=postgresql)
![Allure](https://img.shields.io/badge/Allure-Reports-8A2BE2?style=for-the-badge)
![Jenkins](https://img.shields.io/badge/Jenkins-CI/CD-D24939?style=for-the-badge&logo=jenkins)

</p>

---

## 📖 О проекте

**PFLB Test API** — дипломный проект школы автоматизации тестирования **Performance Lab**.

Цель проекта — разработка единого фреймворка автоматизированного тестирования учебного полигона Performance Lab с
использованием трех уровней тестирования:

- UI Automation
- API Automation
- JDBC Validation

Проект автоматизирует проверку CRUD-операций над сущностями:

- 👤 User
- 🏠 House
- 🚗 Car

а также реализует проверку бизнес-логики приложения, интеграцию с CI/CD и генерацию подробной отчетности.

---

# ⭐ Основные возможности

- UI-тестирование пользовательского интерфейса
- API-тестирование REST-сервисов
- JDBC-проверка данных в PostgreSQL
- Автоматизация CRUD-операций
- Кросс-браузерное тестирование
- Генерация Allure Report
- Jenkins Pipeline
- Retry-механизм
- TestNG Listeners
- Генерация тестовых данных

---

# 🛠 Технологический стек

| Категория      | Используемые технологии |
|----------------|-------------------------|
| Язык           | Java 17                 |
| UI Automation  | Selenide                |
| API Automation | RestAssured             |
| Database       | PostgreSQL + JDBC       |
| Test Framework | TestNG                  |
| Build Tool     | Maven                   |
| Reports        | Allure Report           |
| CI/CD          | Jenkins                 |
| Logging        | Log4j2                  |
| Test Data      | JavaFaker               |
| Utilities      | Lombok                  |

---

# 🏗 Архитектура проекта

```text
                    Automated Tests
     ┌──────────────┬──────────────┬──────────────┐
     │              │              │
  UI Tests      API Tests     JDBC Tests
     │              │              │
     └──────────────┴──────────────┘
                    │
              Business Layer
                    │
      ┌─────────────┴─────────────┐
      │                           │
  Page Objects               API Adapters
      │                           │
      └─────────────┬─────────────┘
                    │
              Performance Lab API
                    │
               PostgreSQL Database
```

---

# 📂 Структура проекта

```text
src
│
├── main
│   ├── api
│   │   ├── adapters
│   │   └── models
│   │
│   ├── db
│   │
│   └── ui
│       ├── dto
│       ├── pages
│       └── wrappers
│
└── test
    ├── api
    ├── jdbc
    ├── listeners
    ├── ui
    └── utils
```

---

# 📈 Проект в цифрах

| Показатель             | Значение           |
|------------------------|--------------------|
| 👥 Команда             | 6 участников       |
| 🧪 Уровни тестирования | UI • API • JDBC    |
| 🏠 Основные сущности   | User • House • Car |
| ⚙ CI/CD                | Jenkins            |
| 📊 Отчетность          | Allure             |
| ☕ Язык разработки      | Java 17            |

---
