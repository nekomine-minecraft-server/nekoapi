package net.nekomine.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements BaseModel<String> {
    private String roleName;
    private int level;

    @Override
    public String getId() {
        return roleName.toLowerCase();
    }
}
