package dev.nicmora.ruleengine.domain.service;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utilidad central para comparar dos valores expresados como String que pueden representar:
 *  - LocalDateTime (varios formatos comunes)
 *  - LocalDate
 *  - Número (BigDecimal)
 *  - Texto (fallback)
 *
 * Reglas:
 *  - Se intenta parsear en orden: LocalDateTime, LocalDate, Número. El primer parse exitoso determina el tipo.
 *  - Si ambos son temporales y uno es LocalDate y otro LocalDateTime, se convierte el LocalDate a LocalDateTime a 00:00:00 para comparar.
 *  - Si ambos son números se usa BigDecimal.compareTo para evitar problemas de precisión/escala.
 *  - Si los tipos difieren (por ejemplo número vs fecha), se usa comparación lexicográfica final (String.compareTo) después de trim.
 */
@Slf4j
public final class GenericValueComparator {

    private GenericValueComparator() {}

    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = new ArrayList<>() {{
        add(DateTimeFormatter.ISO_LOCAL_DATE_TIME);                // 2025-09-18T10:30:15.123 / 2025-09-18T10:30:15
        add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));  // 2025-09-18 10:30:15
        add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));     // 2025-09-18 10:30
        add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));   // 2025-09-18T10:30
    }};

    private static final List<DateTimeFormatter> DATE_FORMATTERS = new ArrayList<>() {{
        add(DateTimeFormatter.ISO_LOCAL_DATE);                     // 2025-09-18
    }};

    private enum ValueType { DATE_TIME, DATE, NUMBER, STRING }

    private record Parsed(ValueType type, Object value) {}

    public static int compare(String left, String right) {
        Objects.requireNonNull(left, "left no puede ser null");
        Objects.requireNonNull(right, "right no puede ser null");
        String lTrim = left.trim();
        String rTrim = right.trim();

        Parsed pLeft = parse(lTrim);
        Parsed pRight = parse(rTrim);

        if (pLeft.type == pRight.type) {
            return switch (pLeft.type) {
                case DATE_TIME -> ((LocalDateTime) pLeft.value).compareTo((LocalDateTime) pRight.value);
                case DATE -> ((LocalDate) pLeft.value).compareTo((LocalDate) pRight.value);
                case NUMBER -> ((BigDecimal) pLeft.value).compareTo((BigDecimal) pRight.value);
                case STRING -> lTrim.compareTo(rTrim);
            };
        }

        if (isTemporal(pLeft.type) && isTemporal(pRight.type)) {
            LocalDateTime ldtLeft = toDateTime(pLeft);
            LocalDateTime ldtRight = toDateTime(pRight);
            return ldtLeft.compareTo(ldtRight);
        }

        log.debug("Comparación entre tipos distintos ({} vs {}), se usa orden lexicográfico.", pLeft.type, pRight.type);
        return lTrim.compareTo(rTrim);
    }

    public static boolean equalsSemantic(String left, String right) {
        return compare(left, right) == 0;
    }

    private static boolean isTemporal(ValueType type) {
        return type == ValueType.DATE_TIME || type == ValueType.DATE;
    }

    private static LocalDateTime toDateTime(Parsed parsed) {
        if (parsed.type == ValueType.DATE_TIME) return (LocalDateTime) parsed.value;
        if (parsed.type == ValueType.DATE) return ((LocalDate) parsed.value).atStartOfDay();
        throw new IllegalArgumentException("No es temporal: " + parsed.type);
    }

    private static Parsed parse(String raw) {
        for (DateTimeFormatter f : DATE_TIME_FORMATTERS) {
            try {
                return new Parsed(ValueType.DATE_TIME, LocalDateTime.parse(raw, f));
            } catch (DateTimeParseException ignored) { }
        }
        for (DateTimeFormatter f : DATE_FORMATTERS) {
            try {
                return new Parsed(ValueType.DATE, LocalDate.parse(raw, f));
            } catch (DateTimeParseException ignored) { }
        }
        try {
            BigDecimal bd = new BigDecimal(raw.replace(",", "."));
            return new Parsed(ValueType.NUMBER, bd);
        } catch (NumberFormatException ignored) { }
        return new Parsed(ValueType.STRING, raw);
    }
}

