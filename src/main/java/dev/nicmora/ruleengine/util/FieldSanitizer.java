package dev.nicmora.ruleengine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para sanitizar campos de mapas, convirtiendo valores problemáticos a null.
 * Esta clase se encarga de normalizar valores como "null", "", " ", "undefined", etc.
 */
public class FieldSanitizer {

    private FieldSanitizer() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Sanitiza los campos del mapa, convirtiendo valores problemáticos a null.
     * Valores como "null", "", " ", "NULL", "undefined", etc. se convierten a null real.
     *
     * @param fields Mapa de campos a sanitizar
     * @return Mapa con campos sanitizados, o null si el mapa de entrada es null
     */
    public static Map<String, String> sanitizeFields(Map<String, String> fields) {
        if (fields == null) {
            return null;
        }

        Map<String, String> sanitizedFields = new HashMap<>();

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Sanitizar el valor
            String sanitizedValue = sanitizeValue(value);
            sanitizedFields.put(key, sanitizedValue);
        }

        return sanitizedFields;
    }

    /**
     * Sanitiza un valor individual, convirtiendo valores problemáticos a null.
     * <p>
     * Los siguientes valores se convierten a null:
     * <ul>
     *   <li>null</li>
     *   <li>Cadenas vacías ""</li>
     *   <li>Cadenas con solo espacios en blanco</li>
     *   <li>"null" (case-insensitive)</li>
     *   <li>"undefined" (case-insensitive)</li>
     * </ul>
     *
     * @param value Valor a sanitizar
     * @return Valor sanitizado o null si es un valor problemático
     */
    public static String sanitizeValue(String value) {
        if (value == null) {
            return null;
        }

        String trimmedValue = value.trim();

        // Convertir valores problemáticos a null
        if (trimmedValue.isEmpty() ||
            trimmedValue.equalsIgnoreCase("null") ||
            trimmedValue.equalsIgnoreCase("undefined")) {
            return null;
        }

        return value;
    }
}


