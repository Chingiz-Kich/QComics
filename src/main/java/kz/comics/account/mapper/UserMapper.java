package kz.comics.account.mapper;

import kz.comics.account.model.user.UserDto;
import kz.comics.account.repository.entities.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserMapper {

   public UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();

        if (StringUtils.isNotBlank(userDto.getAvatarBase64())) {
            userEntity.setAvatar(Base64.getDecoder().decode(userDto.getAvatarBase64()));
        }

        return userEntity;
    }

    public UserDto toDto(UserEntity userEntity) {
      UserDto userDto = UserDto.builder()
               .id(userEntity.getId())
               .username(userEntity.getUsername())
               .email(userEntity.getEmail())
               .role(userEntity.getRole())
               .build();

      if (userEntity.getAvatar() != null) {
          userDto.setAvatarBase64(Base64.getEncoder().encodeToString(userEntity.getAvatar()));
      }

      return userDto;
    }
}
