# Role-Based Access Control Implementation Plan

## Overview

Update the Toda application to support three user roles: **admin**, **tourist**, and **tourguide**, with proper middleware for role-based access control.

---

## Current State

- Existing roles: `USER`, `ADMIN` (in [`Role.java`](Toda/src/main/java/com/example/Toda/Entity/Role.java))
- No role-based endpoint protection
- Empty [`UserController`](Toda/src/main/java/com/example/Toda/controller/UserController.java) and basic [`Admin`](Toda/src/main/java/com/example/Toda/controller/Admin.java) controllers

---

## Implementation Plan

### 1. Update Role Enum (Remove USER, keep ADMIN/TOURIST/TOURGUIDE)

**File:** [`Toda/src/main/java/com/example/Toda/Entity/Role.java`](Toda/src/main/java/com/example/Toda/Entity/Role.java)

```java
public enum Role {
    ADMIN,
    TOURIST,
    TOURGUIDE
}
```

---

### 2. Update RegisterRequest DTO

**File:** [`Toda/src/main/java/com/example/Toda/DTO/RegisterRequest.java`](Toda/src/main/java/com/example/Toda/DTO/RegisterRequest.java)

Add role field with default value:

```java
package com.example.Toda.DTO;

import com.example.Toda.Entity.Role;

public record RegisterRequest(
        String username,
        String email,
        String password,
        Role role  // New field
) {
    // Constructor with default role
    public RegisterRequest(String username, String email, String password) {
        this(username, email, password, Role.TOURIST);
    }
}
```

---

### 3. Update authService

**File:** [`Toda/src/main/java/com/example/Toda/service/authService.java`](Toda/src/main/java/com/example/Toda/service/authService.java)

Update signUp method to use role from request:

```java
public authResponse signUp(RegisterRequest registerRequest) {
    if (userRepo.findByEmail(registerRequest.email()).isPresent()) {
        throw new UserAlreadyExistsException("User already exists");
    }

    UserEntity newUser = new UserEntity();
    newUser.setUsername(registerRequest.username());
    newUser.setEmail(registerRequest.email());
    newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
    newUser.setRole(registerRequest.role());  // Use role from request
    userRepo.save(newUser);
    UserPrincipal userPrincipal = new UserPrincipal(newUser);
    String token = jwtService.generateToken(claims, userPrincipal);

    return new authResponse(token);
}
```

---

### 4. Update SecurityConfig (Middleware)

**File:** [`Toda/src/main/java/com/example/Toda/config/SecurityConfig.java`](Toda/src/main/java/com/example/Toda/config/SecurityConfig.java)

Add role-based endpoint protection:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(request -> {
            var opt = new org.springframework.web.cors.CorsConfiguration();
            opt.setAllowedOrigins(java.util.List.of("*"));
            opt.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            opt.setAllowedHeaders(java.util.List.of("*"));
            return opt;
        }))
        .authorizeHttpRequests(auth -> auth
            // Public endpoints
            .requestMatchers("/api/auth/**").permitAll()

            // Admin-only endpoints
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // TourGuide-only endpoints
            .requestMatchers("/api/tourguide/**").hasRole("TOURGUIDE")

            // Tourist-only endpoints
            .requestMatchers("/api/tourist/**").hasRole("TOURIST")

            // Any other request requires authentication
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
```

---

### 5. Update Admin Controller

**File:** [`Toda/src/main/java/com/example/Toda/controller/Admin.java`](Toda/src/main/java/com/example/Toda/controller/Admin.java)

```java
package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class Admin {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard - Welcome!";
    }

    @GetMapping("/users")
    public String getAllUsers() {
        return "List of all users";
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam String newRole) {
        return "User role updated";
    }
}
```

---

### 6. Create TourGuide Controller

**File:** `Toda/src/main/java/com/example/Toda/controller/TourGuideController.java` (NEW)

```java
package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourguide")
@PreAuthorize("hasRole('TOURGUIDE')")
public class TourGuideController {

    @GetMapping("/profile")
    public String getProfile() {
        return "Tour Guide Profile";
    }

    @GetMapping("/tours")
    public String getMyTours() {
        return "My tours list";
    }

    @PostMapping("/tours")
    public String createTour() {
        return "Tour created";
    }
}
```

---

### 7. Create Tourist Controller

**File:** `Toda/src/main/java/com/example/Toda/controller/TouristController.java` (NEW)

```java
package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourist")
@PreAuthorize("hasRole('TOURIST')")
public class TouristController {

    @GetMapping("/profile")
    public String getProfile() {
        return "Tourist Profile";
    }

    @GetMapping("/bookings")
    public String getMyBookings() {
        return "My bookings list";
    }

    @PostMapping("/bookings")
    public String bookTour() {
        return "Tour booked";
    }
}
```

---

## API Endpoints Summary

### Public Endpoints

| Endpoint                   | Method | Access |
| -------------------------- | ------ | ------ |
| `/api/auth/login`          | POST   | Public |
| `/api/auth/signup`         | POST   | Public |
| `/api/auth/forgetPassword` | POST   | Public |
| `/api/auth/verify`         | POST   | Public |
| `/api/auth/ResetPassword`  | POST   | Public |

### Admin-Only Endpoints

| Endpoint                     | Method | Access |
| ---------------------------- | ------ | ------ |
| `/api/admin/dashboard`       | GET    | ADMIN  |
| `/api/admin/users`           | GET    | ADMIN  |
| `/api/admin/users/{id}/role` | POST   | ADMIN  |

### TourGuide-Only Endpoints

| Endpoint                 | Method | Access    |
| ------------------------ | ------ | --------- |
| `/api/tourguide/profile` | GET    | TOURGUIDE |
| `/api/tourguide/tours`   | GET    | TOURGUIDE |
| `/api/tourguide/tours`   | POST   | TOURGUIDE |

### Tourist-Only Endpoints

| Endpoint                | Method | Access  |
| ----------------------- | ------ | ------- |
| `/api/tourist/profile`  | GET    | TOURIST |
| `/api/tourist/bookings` | GET    | TOURIST |
| `/api/tourist/bookings` | POST   | TOURIST |

---

## Database Migration

Since the `Role` enum is changing, the database will be updated automatically due to `spring.jpa.hibernate.ddl-auto=update` in [`application.properties`](Toda/src/main/resources/application.properties:7).

**Note:** Existing users with old roles (USER, ADMIN) may need manual migration or the application should handle this gracefully.

---

## Testing Checklist

- [ ] Admin can access `/api/admin/**` endpoints
- [ ] TourGuide can access `/api/tourguide/**` endpoints
- [ ] Tourist can access `/api/tourist/**` endpoints
- [ ] Admin cannot access `/api/tourguide/**` endpoints
- [ ] TourGuide cannot access `/api/admin/**` endpoints
- [ ] Tourist cannot access `/api/admin/**` or `/api/tourguide/**` endpoints
- [ ] Unauthenticated users cannot access protected endpoints
- [ ] Registration with role selection works correctly
- [ ] Default role (TOURIST) is applied when not specified
