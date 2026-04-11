package com.gobb.repair.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SummaryUtil {

    private SummaryUtil() {}

    public static String buildUpdateSummary(Object before, Object after, Map<String, String> fieldLabels) {
        List<String> changes = new ArrayList<>();

        for (Field field : before.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            if (!fieldLabels.containsKey(fieldName)) continue;

            try {
                Object beforeVal = field.get(before);
                Object afterVal = field.get(after);

                if (beforeVal == null || afterVal == null) continue;
                if (beforeVal.equals(afterVal)) continue;

                changes.add(String.format("<strong>%s :</strong><br />&nbsp;%s ▶ %s",
                        fieldLabels.get(fieldName), beforeVal, afterVal));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return String.join("|", changes);
    }}