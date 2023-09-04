package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void verificationTransaction(User user, BigDecimal amount) throws Exception {
		if (user.getUserType() == UserType.MERCHANT) {
			throw new Exception("Lojista não faz transferência");
		}
		
		if (user.getBalance().compareTo(amount) < 0) {
			throw new Exception("Saldo insuficiente");
		}
	}
	
	public User findUserById(Long id) throws Exception {
		return this.userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
	}
	
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
	
	public User createUser(UserDTO data) {
		User newUser = new User(data);
		this.saveUser(newUser);
		
		return newUser;
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
}
