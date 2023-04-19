/*
package kz.comics.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comics")
public class Comics {

    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    private List<String> genre;
    private String author;
    private Base64 cover;
    private List<Base64> chapters;
    private Integer likes;

}
*/
