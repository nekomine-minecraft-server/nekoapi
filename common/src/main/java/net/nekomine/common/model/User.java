package net.nekomine.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.nekomine.common.record.Skin;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements BaseModel<String> {
    private String userName;
    private String password;
    private Skin skin;
    private Date created;

    private Map<String, Double> vaultMap = new HashMap<>();
    private Map<String, String> propertyMap = new HashMap<>();
    private List<Role> roles = new ArrayList<>();

    @Override
    public String getId() {
        return userName.toLowerCase();
    }
}
