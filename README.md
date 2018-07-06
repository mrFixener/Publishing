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
GET endpoint **[server:port]/authors**

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
GET endpoint **[server:port]/author/info/short/{authorId}**
{authorId} - id автора
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

Создание автора (только для пользователя с ролью ADMIN)
================
POST endpoint **[server:port]/author/create**
genre - [MALE,FEMALE]
#### Пример запроса
```shell
http://localhost:8080/author/create

{
	"firstName": "FirstName",
	"lastName": "LastName",
	"sex": "MALE",
	"birthDate": "1985-05-20",
	"rewards":[{"year":2014, "title":"American reward"}]
}
```
#### Ответ
```json
{
    "id": 12,
    "firstName": "FirstName",
    "lastName": "LastName",
    "sex": "MALE",
    "books": [],
    "rewards": [
        {
            "id": 36,
            "year": 2014,
            "title": "American reward"
        }
    ],
    "birthDate": "1985-05-20T00:00:00.000+0000"
}
```

Обновление автора (только для пользователя с ролью ADMIN)
================
PUT endpoint **[server:port]/author/update/{authorId}**
{authorId} - id автора
genre - [ADVENTURE, FANTASY, PROGRAMMING, ROMANCE, COMEDY, MYSTERY]
#### Пример запроса
```shell
http://localhost:8080/author/update/12

{
	"books":  [{"id": 1, "title":"Scala for the impatient", "isbn":"978-5-94074-920-2", "genre":"PROGRAMMING"}],
	"rewards":[{"id": 36, "year": 2014, "title": "Best author"}]
}
```
#### Ответ
```json
{
    "id": 12,
    "firstName": "FirstName",
    "lastName": "LastName",
    "sex": "MALE",
    "books": [
        {
            "id": 1,
            "title": "Scala for the impatient",
            "isbn": "978-5-94074-920-2",
            "genre": "PROGRAMMING",
            "authors": [
                {
                    "id": 1,
                    "firstName": "Cay",
                    "lastName": "S. Hostmann",
                    "sex": "MALE",
                    "books": [
                        1,
                        {
                            "id": 2,
                            "title": "Core Java for the impatient",
                            "isbn": "123-456-789",
                            "genre": "PROGRAMMING",
                            "authors": [
                                1
                            ]
                        },
                        {
                            "id": 3,
                            "title": "Java. Library professional",
                            "isbn": "978-5-94074-920-3",
                            "genre": "PROGRAMMING",
                            "authors": [
                                1,
                                {
                                    "id": 2,
                                    "firstName": "Gary",
                                    "lastName": "Cornell",
                                    "sex": "MALE",
                                    "books": [
                                        3,
                                        {
                                            "id": 4,
                                            "title": "Java. Library professional. Volume 2",
                                            "isbn": "978-5-94074-920-4",
                                            "genre": "PROGRAMMING",
                                            "authors": [
                                                1,
                                                2,
                                                {
                                                    "id": 9,
                                                    "firstName": "TestName",
                                                    "lastName": "TestLastName",
                                                    "sex": "FEMALE",
                                                    "books": [
                                                        3,
                                                        4
                                                    ],
                                                    "rewards": [],
                                                    "birthDate": "1993-07-04T21:00:00.000+0000"
                                                }
                                            ]
                                        }
                                    ],
                                    "rewards": [
                                        {
                                            "id": 31,
                                            "year": 2016,
                                            "title": "America Award"
                                        }
                                    ],
                                    "birthDate": "1961-09-24T22:00:00.000+0000"
                                },
                                9
                            ]
                        },
                        4
                    ],
                    "rewards": [
                        {
                            "id": 32,
                            "year": 2010,
                            "title": "Best Author"
                        }
                    ],
                    "birthDate": "1959-06-15T22:00:00.000+0000"
                },
                {
                    "id": 12,
                    "firstName": "FirstName",
                    "lastName": "LastName",
                    "sex": "MALE",
                    "books": [
                        1
                    ],
                    "rewards": [
                        {
                            "id": 36,
                            "year": 2014,
                            "title": "Best author"
                        }
                    ],
                    "birthDate": "1985-05-19T21:00:00.000+0000"
                }
            ]
        }
    ],
    "rewards": [
        {
            "id": 36,
            "year": 2014,
            "title": "Best author"
        }
    ],
    "birthDate": "1985-05-19T21:00:00.000+0000"
}
```

Удаление автора (только для пользователя с ролью ADMIN)
================
DELETE endpoint **[server:port]/author/delete/{authorId}**
{authorId} - id автора

#### Пример запроса
```shell
http://localhost:8080/author/delete/12
```
