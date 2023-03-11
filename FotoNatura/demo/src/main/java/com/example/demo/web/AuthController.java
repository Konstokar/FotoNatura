package com.example.demo.web;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.payload.response.JWTTokenSuccessResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.security.SecurityConstants;
import com.example.demo.services.UserService;
import com.example.demo.validations.ResponceErrorValidaton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // проверяет выражение перед входом в метод
// источниками только для конкретного метода
// Controller
import org.springframework.security.authentication.AuthenticationManager; // Обрабатывает Authentication запрос.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; // Представляет токен для запроса проверки подлинности или для
// участника, прошедшего проверку подлинности, после обработки запроса методом
// AuthenticationManager.authenticate(Authentication).
import org.springframework.security.core.context.SecurityContextHolder; // Связывает данное SecurityContext с текущим
// потоком выполнения.
import org.springframework.util.ObjectUtils; // Различные служебные методы объекта.
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid; // Проверка уровня метода + пометка атрибута для проверки

@CrossOrigin
@RestController
@RequestMapping("/api/auth") // "..." - URL-адрес
@PreAuthorize("permitAll()") // указание того, что URL-адреса разрешены всем
public class AuthController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponceErrorValidaton responceErrorValidaton;
    @Autowired
    private UserService userService;

    public ResponseEntity<Object> authenticationUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){ // Аннотация
        // @RequestBody сопоставляет тело HttpRequest с объектом передачи или домена, обеспечивая автоматическую десериализацию входящего тела
        // HttpRequest в объект Java
        ResponseEntity<Object> errors = responceErrorValidaton.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        )); // .authenticate(...) - авторизация пользователя
        SecurityContextHolder.getContext().setAuthentication(authentication); // .getContext() - получение текущего
        // SecurityContext
        // .setAuthentication(...) - Изменяет текущего участника, прошедшего проверку подлинности, или удаляет
        // информацию проверки подлинности.
        String jwt = SecurityConstants.TOKEN_PREFIX +  jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signupRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responceErrorValidaton.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully")); // возврат положительного
        // результата
    }
}
