🚀 PFLB Test API
> **Дипломный проект школы автоматизации тестирования Performance Lab (5 поток)**
> Автоматизированное тестирование тестового полигона Performance Lab, включающее UI, API и JDBC-проверки CRUD-операций над
> сущностями User, House и Car.
---
📑 Содержание
Возможности
Технологический стек
Покрытие
Архитектура
Структура проекта
Команда
Реализованные проверки
CI/CD
Известные дефекты
Запуск
Отчётность
Архитектурные решения
---
✨ Возможности
✅ UI-тестирование (Selenide)
✅ API-тестирование (RestAssured)
✅ JDBC-проверки PostgreSQL
✅ CRUD для User / House / Car
✅ Cross Browser Testing
✅ Jenkins CI/CD
✅ Allure Reports
✅ Retry Mechanism
---
🛠 Технологический стек
Категория Технологии
Язык Java 17
Сборка Maven
UI Selenide
API RestAssured
База данных PostgreSQL + JDBC
Тестирование TestNG
Отчёты Allure
CI/CD Jenkins
Логирование Log4j2
Генерация данных JavaFaker
Дополнительно Lombok
---
📊 Покрытие
Модуль UI API JDBC
User ✅ ✅ ✅
House ✅ ✅ ✅
Car ✅ ✅ ✅
Delete ✅ — ✅
All Post ✅ — ✅
---
🏗 Архитектура

```text
UI Tests      API Tests      JDBC Tests
      \          |          /
       \         |         /
      Business / Step Layer
               |
     Page Objects / Adapters
               |
            REST API
               |
          PostgreSQL
```

---
📂 Структура проекта

```text
src
├── main
│   ├── api
│   ├── db
│   └── ui
└── test
    ├── api
    ├── jdbc
    ├── listeners
    ├── ui
    └── utils
```

---
👥 Команда
Участник Основной функционал
Олег Егоров Авторизация, базовая инфраструктура
Андрей Якушин Users, Money, Buy/Sell Car, JDBC
Юлия Шемякина All Post
Ольга Тощевикова Cars
Константин Кучма Delete, Retry
Татьяна Лямкина Houses
<details>
<summary><b>Подробнее о вкладе участников</b></summary>
Олег Егоров
UI/API авторизация
BaseTest, BasePage, BaseAdapter, BaseAPITest
Получение JWT
Сортировка пользователей
Андрей Якушин
Пополнение баланса
Покупка/продажа автомобиля
Заселение/выселение
JDBC-проверка баланса
TestListener
YakushinTests.xml
Юлия Шемякина
All Post
Создание User, House, Car
JDBC-проверки
Ольга Тощевикова
CRUD Car
JSON Schema
CarDBConnection
Константин Кучма
Delete User/House/Car
Retry Analyzer
Annotation Transformer
Татьяна Лямкина
CRUD House
Read All / Read By ID
Проверки домов
</details>
---
📋 Реализованные проверки
<details>
<summary><b>UI</b></summary>
Авторизация
CRUD
Сортировка
All Post
Пополнение баланса
Покупка/продажа автомобиля
Заселение/выселение
Проверка отображения элементов
Позитивные и негативные сценарии
</details>
<details>
<summary><b>API</b></summary>
CRUD User
CRUD House
CRUD Car
Money
Sell Car
Settle User
Проверка HTTP Status
JSON Schema Validation
</details>
<details>
<summary><b>JDBC</b></summary>
Проверка пользователей
Проверка домов
Проверка автомобилей
Проверка баланса
Проверка удаления
</details>
---
⚙ CI/CD
Pipeline Jenkins выполняет:
Checkout проекта
Maven Build
Запуск TestNG
Генерацию Allure Report
Публикацию JUnit Report
Поддерживаемые параметры:
Параметр	Значение
BROWSER	chrome / firefox / edge
TEST_USER	пользователь полигона
TEST_PASSWORD	пароль пользователя
---
🐞 Известные дефекты
№	Описание
1	Продажа отсутствующей машины возвращает HTTP 200 вместо ожидаемого HTTP 404
2	GET `/user/{userId}/cars` возвращает HTTP 204 вместо ожидаемого HTTP 200
---
▶ Запуск
```bash
mvn clean test \
-Dheadless=true \
-Dbrowser=chrome \
-Dtest.user=user@pflb.ru \
-Dtest.password=***
```
---
📈 Отчётность
Allure Report
JUnit Report
Jenkins Pipeline
---
🏛 Архитектурные решения
Page Object Model
Adapter Pattern
DTO
Base Test Architecture
Retry Analyzer
Test Listener
Parameterized Test Suites
Cross Browser Testing
---
🎯 Итог
Проект представляет собой комплексный фреймворк автоматизированного тестирования, объединяющий UI, API и JDBC-проверки с использованием современных практик автоматизации и интеграцией в процесс CI/CD.
> Учётные данные тестового полигона и параметры подключения к базе данных намеренно не публикуются в репозитории.