package br.com.oxy.ssgt.infra.gateways;


import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.persistence.UserEntity;
import br.com.oxy.ssgt.infra.persistence.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryJPA implements UserRepositoryApplication {

    private UserRepository repository;
    private UserEntityMapper mapper;

    public UserRepositoryJPA(UserRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User registerUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
