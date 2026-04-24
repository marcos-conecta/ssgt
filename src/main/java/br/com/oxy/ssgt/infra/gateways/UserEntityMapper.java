package br.com.oxy.ssgt.infra.gateways;

import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.persistence.UserEntity;

public class UserEntityMapper {

    public UserEntity toEntity(User user){
        return new UserEntity(user.getName(), user.getEmail(), user.getPassword());
    }

    public User toDomain(UserEntity userEntity){
        return new User(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
    }
}
