package net.nekomine.spigot.utility;

import com.google.common.base.CaseFormat;
import lombok.experimental.UtilityClass;
import net.nekomine.common.model.BaseModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ModelFormat {

    public List<String> modelToListString(BaseModel<String> model) {
        List<String> dtoList = new ArrayList<>();

        String className = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, model.getClass().getSimpleName()) + "_MODEL";
        dtoList.add("Информация о " + className + " §c§l" + model.getId() + " - §f:");

        Map<String, String> dtoMap = convertToMap(model);
        dtoMap.forEach((key, value) -> dtoList.add(" §c▪ §f" + className + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, key) + " - §7" + value));

        return dtoList;
    }

    private Map<String, String> convertToMap(Object dto) {
        Map<String, String> resultMap = new HashMap<>();

        Class<?> dtoClass = dto.getClass();
        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(dto);

                if (value != null) {
                    resultMap.put(fieldName, value.toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return resultMap;
    }
}
