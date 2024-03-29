package kr.co.parkseungsu.eatgo.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @NotEmpty
    private String email;
    @Setter
    @NotEmpty
    private String name;
    @Setter
    @NotNull
    private Long level;
    @NotEmpty
    private String password;

    private Long restaurantId;

    public boolean isAdmin() {
        return level>=100L;
    }

    public boolean isActive() {
        return level>0;
    }

    public void setRestaurantId(Long restaurantId) {
        this.level=50L;
        this.restaurantId = restaurantId;
    }


    public void deactivate() {
        level=0L;
    }

    public boolean isRestaurantOwner() {
        return level==50;
    }
}
