[![Build Status](https://travis-ci.com/mrFixener/Publishing.svg?branch=master)](https://travis-ci.com/mrFixener/Publishing)

Конфигурация приложения
================
После 'клонирования' данного приложения, нужно настроить коннект к БД, а также указать используемую БД.
Произвести нужно это в 2 - х файлах: продакшн, тест. [Для ускорения настройки - возможно использовать одни и те же настройки подключения для обоих сред]
```properties
#другие настройки
#.......................................
spring.datasource.url=jdbc:mysql://localhost:3306/publishing
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver.class=com.mysql.jdbc.Driver
#.......................................
```

