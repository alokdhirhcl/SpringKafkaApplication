package com.example.emp.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.emp.domain.User;
import com.example.emp.repository.UserRepository;
@Service
public class UserService {
    
    @Autowired
    private UserRepository userrepo;
    
    public List<User> listAll() {
        return userrepo.findAll();
    }
     
   
}