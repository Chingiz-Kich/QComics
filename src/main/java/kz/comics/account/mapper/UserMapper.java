package kz.comics.account.mapper;

import kz.comics.account.model.user.UserDto;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserMapper {

   public UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .username(userDto.getUsername())
                .avatar(Base64.getDecoder().decode(userDto.getAvatarBase64()))
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public UserDto toDto(UserEntity userEntity) {
       return UserDto.builder()
               .id(userEntity.getId())
               .username(userEntity.getUsername())
               .avatarBase64(Base64.getEncoder().encodeToString(userEntity.getAvatar()))
               .email(userEntity.getEmail())
               .role(userEntity.getRole())
               .build();
    }
}
