
# ![Шапка чеклиста](https://github.com/Tanusha-tech/Tanusha-tech/blob/main/header.png)

## Содержание
- [Технологии](#технологии)
- 
- 

## Технологии
 - [Java](https://www.oracle.com/java/technologies/downloads/)
 - [Maven](https://maven.apache.org/)
 - [Selenide](https://selenide.org/)
 - [RestAssured](https://rest-assured.io/)
 - [Test NG](https://testng.org/)
 - [Allure](https://allurereport.org/docs/)
 - [Log4j2](https://logging.apache.org/log4j/3.x/) & [Lombok](https://projectlombok.org/)
 - [JDBC](https://dev.mysql.com/downloads/connector/j/)

---
## "Houses" -> "Read all" <http://82.142.167.37:4881/#/read/houses>
#### Проверка перехода по ссылке Read all
#### Отображение управляющего элемента "Reload"
#### На форме в таблице присутствуют столбцы:
* ID
* Floor Count
* Price
* Parking Places
* Lodgers
#### Проверка отображения данных созданного (существующего) пользователя в таблице
#### Проверка обновления данных пользователя в таблице по кнопке "Reload"

---
## "Houses" -> "Read one by ID" <http://82.142.167.37:4881/#/read/house>:
### Функциональные проверки (позитивные)
#### Проверка перехода по ссылке Read one by ID
#### Отображение поля ввода поиска по ID дома
#### Отображение таблицы Houses:
* ID
* Floor Count
* Price
* Parking Places
* Lodgers
#### Отображение таблицы Lodgers:
* ID
* First name
* Last name
* Age
* Sex
* Money
#### Отображение таблицы Parking:
* ID
* isWarm
* isCovered
* placesCount
#### Кнопка "Read" присутствует на форме
#### Поле с отображением статуса запроса присутствует на форме
#### Проверка отображения данных созданного (существующего) пользователя в таблице

---
## "Houses" -> "Create new" <http://82.142.167.37:4881/#/create/house>:
#### Функциональные проверки
* Переход по ссылке Create new
* Заполнение всех полей и отправка формы
* Заполнение только обязательных полей (Floors и Price), оставив необязательные пустыми
* Ввод максимально допустимого количества символов в текстовые поля
---
#### Негативные проверки
* Отправить форму с пустыми обязательными полями
* Ввод данных, превышающие максимальную длину полей
* Ввод невалидных числовые значений