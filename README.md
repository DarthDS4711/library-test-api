# Library API

Este proyecto es una **API REST** construida con **Spring Boot 2.7**, **Java 8** y **MyBatis**.
El paquete base del proyecto es:

```
com.library.library_api
```

Dentro de este paquete se encuentran los siguientes subpaquetes principales:

* **controller** → Controladores REST donde se definen los endpoints.
* **dto** → Clases Data Transfer Object para transportar datos entre capas.
* **model** → Entidades o modelos de dominio.
* **repository** → Interfaces de persistencia con MyBatis (mappers).
* **resources** → Archivos de configuración, como `application.properties`, `application.yml`

---

## Requisitos previos

* **Java 8**
* **Maven 3.6+**
* **Spring Boot 2.7.x**
* **MyBatis Spring Boot Starter**
* Una base de datos compatible (OracleDB)

---

## Ejemplo de modelo

```java
package com.library.library_api.model;

public class Book {
    private Long id;
    private String title;
    private String author;

    // Getters y Setters
}
```

---

## Ejemplo de DTO

```java
package com.library.library_api.dto;

public class BookDTO {
    private String title;
    private String author;

    // Getters y Setters
}
```

---

## Ejemplo de Repository con MyBatis

```java
package com.library.library_api.repository;

import com.library.library_api.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookRepository {

    @Select("SELECT * FROM books")
    List<Book> findAll();

    @Select("SELECT * FROM books WHERE id = #{id}")
    Book findById(Long id);

    @Insert("INSERT INTO books(title, author) VALUES(#{title}, #{author})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Book book);
}
```

---

## Ejemplo de Controller con Endpoints

```java
package com.library.library_api.controller;

import com.library.library_api.dto.BookDTO;
import com.library.library_api.model.Book;
import com.library.library_api.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Obtener todos los libros
    @GetMapping
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    // Agregar un libro
    @PostMapping
    public Book addBook(@RequestBody BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        repository.insert(book);
        return book;
    }

    // Obtener libro por id
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return repository.findById(id);
    }
}
```

---

## Ejemplo de configuración (`application.properties` en resources)

```properties
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

mybatis.type-aliases-package=com.library.library_api.model
mybatis.mapper-locations=classpath:mapper/*.xml
```

---

## Documentación con Swagger

Este proyecto puede integrarse fácilmente con **Springfox Swagger** o **Springdoc OpenAPI**.



La documentación estará disponible en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
o bien en
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Ejecución

Para compilar y ejecutar la aplicación:

```bash
mvn clean install
mvn spring-boot:run
```

La API estará disponible en:
[http://localhost:8080/api/v1](http://localhost:8080/api/v1)

---

