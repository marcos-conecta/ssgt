package br.com.oxy.ssgt.infra.gateways;

import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.persistence.user.UserEntity;

public class UserEntityMapper {

    public UserEntity toEntity(User user){
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public User toDomain(UserEntity userEntity){
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
    }
}
