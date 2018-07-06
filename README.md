[![Build Status](https://travis-ci.com/mrFixener/Publishing.svg?branch=master)](https://travis-ci.com/mrFixener/Publishing)

Конфигурация приложения
================
После 'клонирования' данного приложения, нужно настроить коннект к БД, а также указать используемую БД.
Произвести нужно это в 2 - х файлах: application.properties - продакшн, test.properties - тест. [Для упрощения настройки и быстрого запуска - возможно использовать одни и те же настройки подключения для обоих сред]
```properties
#другие настройки
#.......................................
spring.datasource.url=jdbc:mysql://localhost:3306/publishing
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver.class=com.mysql.jdbc.Driver
#.......................................
```
Запуск приложения
================
После всех настроек приложения (указано выше) - выполнить:
```shell
gradle bootrun
```
Авторизация приложения
================
Приложение использует Basic Authorization (<a target="_blank" rel="noopener noreferrer" href="https://ibb.co/bC7CKy">см. пример здесь</a>).

Просмотр всех авторов
================
GET endpoint [server:port]/authors

#### Пример запроса
```shell
http://localhost:8080/authors
```
#### Ответ
```json
{
    "content": [
     {
            "id": 2,
            "firstName": "Gary",
            "lastName": "Cornell",
            "sex": "MALE",
            "rewards": [
                {
                    "id": 31,
                    "year": 2016,
                    "title": "America Award"
                }
            ],
            "birthDate": "1961-09-24T22:00:00.000+0000"
        }
        ]
        //.......................
        }
```

Просмотр краткой инфо об авторе
================
GET endpoint [server:port]/authors

#### Пример запроса
```shell
http://localhost:8080/author/info/short/2
```
#### Ответ
```json
{
    "firstName": "Gary",
    "lastName": "Cornell",
    "age": 56,
    "listBooks": [
        "Java. Library professional",
        "Java. Library professional. Volume 2"
    ]
}
```

Создание автора
================
POST endpoint [server:port]/authors

#### Пример запроса
```shell
http://localhost:8080/author/info/short/2
```
#### Ответ
```json
{
    "firstName": "Gary",
    "lastName": "Cornell",
    "age": 56,
    "listBooks": [
        "Java. Library professional",
        "Java. Library professional. Volume 2"
    ]
}
```
