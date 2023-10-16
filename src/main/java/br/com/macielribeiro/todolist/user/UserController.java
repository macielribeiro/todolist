package br.com.macielribeiro.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificador
 * Public
 * Private somente alguns atributos
 * Protected
 * 
 * class é um tipo, poderia ser interface ou enum
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity  create(@RequestBody UserModel userModel) {
       var user =  this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            //System.out.println("Usuário já existe!");
            // Mensagem de erro
            // Status code
            //return ResponseEntity.status(400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        //Criptografando a senha
        var passwordHashered = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashered);

       var userCreated =  this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

  }