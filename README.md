# course-management-system

# Course Management System

## Áttekintés

Ez a projekt egy **Spring Boot alapú REST API alkalmazás**, amely kurzusok, felhasználók és kategóriák kezelésére szolgál.
Az alkalmazás támogatja az alapvető **CRUD műveleteket**, valamint lehetőséget biztosít hallgatók kurzusokra való feliratkozására és kategóriák kurzusokhoz rendelésére.

A projekt **Java 17, Spring Boot, JPA/Hibernate és MySQL** technológiák használatával készült.

---

## Felhasznált technológiák

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL (éles adatbázis)
* H2 adatbázis (teszteléshez)
* Lombok
* JUnit 5
* Mockito
* Postman (API tesztelés)

---

## Projekt struktúra

```id="6qqx4j"
src/main/java/com/example/course_management

controller
 ├ CourseController
 ├ UserController
 └ CategoryController

service
 ├ CourseService
 ├ UserService
 └ CategoryService

repository
 ├ CourseRepository
 ├ UserRepository
 └ CategoryRepository

entity
 ├ Course
 ├ User
 └ Category

dto
 ├ CourseRequestDto
 ├ CourseResponseDto
 ├ UserRequestDto
 ├ UserResponseDto
 ├ CategoryRequestDto
 └ CategoryResponseDto

mapper
 └ CourseMapper
```

---

## Funkciók

### Kurzuskezelés

* Kurzus létrehozása
* Kurzus módosítása
* Kurzus törlése
* Kurzusok listázása
* Kurzus lekérdezése ID alapján

### Felhasználókezelés

* Felhasználó létrehozása
* Felhasználó módosítása
* Felhasználó törlése
* Felhasználók listázása

### Kategóriakezelés

* Kategória létrehozása
* Kategória törlése
* Kategóriák listázása

### Kapcsolatok

* Kurzus oktatója (**ManyToOne kapcsolat**)
* Hallgatók feliratkozása kurzusokra (**ManyToMany kapcsolat**)
* Kategóriák hozzárendelése kurzusokhoz (**ManyToMany kapcsolat**)

---

## REST API végpontok

### Kurzus (Course)

| Módszer | Endpoint            | Leírás                        |
| ------- | ------------------- | ----------------------------- |
| GET     | `/api/courses`      | Összes kurzus lekérdezése     |
| GET     | `/api/courses/{id}` | Kurzus lekérdezése ID alapján |
| POST    | `/api/courses`      | Új kurzus létrehozása         |
| PUT     | `/api/courses/{id}` | Kurzus módosítása             |
| DELETE  | `/api/courses/{id}` | Kurzus törlése                |

További műveletek:

| Módszer | Endpoint                                          | Leírás                               |
| ------- | ------------------------------------------------- | ------------------------------------ |
| POST    | `/api/courses/{courseId}/enroll/{studentId}`      | Hallgató feliratkozása kurzusra      |
| POST    | `/api/courses/{courseId}/categories/{categoryId}` | Kategória hozzárendelése a kurzushoz |

---

## Példa kérés

### Kurzus létrehozása

```json id="htv8el"
{
  "title": "Spring Boot Advanced",
  "description": "Haladó backend fejlesztés",
  "maxParticipants": 20,
  "startDate": "2026-05-01",
  "instructorId": 1
}
```

---

## Validáció

A DTO osztályok validációs annotációkat használnak, például:

* `@NotBlank`
* `@NotNull`
* `@Email`

---

## Hibakezelés

Az alkalmazás egyedi kivételkezelést használ:

* `ResourceNotFoundException`

---

## Tesztelés

A projekt unit teszteket tartalmaz a következő technológiákkal:

* **JUnit 5**
* **Mockito**

Példa teszt:

```java id="1ovpns"
@Test
void testCreateCourse_UserNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> {
        courseService.createCourse(requestDto);
    });
}
```

---

## UML és adatbázis diagramok

A projekt dokumentációja tartalmazza:

* UML osztálydiagram
* UML szekvencia diagram
* ER adatbázis diagram

Ezek bemutatják az entitások közötti kapcsolatokat és az alkalmazás működését.