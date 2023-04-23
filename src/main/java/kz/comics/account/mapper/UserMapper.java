package kz.comics.account.mapper;

import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

   public UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public UserDto toDto(UserEntity userEntity) {
       return UserDto.builder()
               .username(userEntity.getUsername())
               .password(userEntity.getPassword())
               .email(userEntity.getEmail())
               .role(userEntity.getRole())
               .build();
    }
}