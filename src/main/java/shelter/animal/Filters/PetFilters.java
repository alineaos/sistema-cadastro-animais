package shelter.animal.Filters;

import java.util.HashMap;
import java.util.Map;

public class PetFilters {
    private String portugueseWord;
    private String EnglishFilter;

    public PetFilters(String portugueseWord, String filter) {
        this.portugueseWord = portugueseWord;
        this.EnglishFilter = filter;
    }

    public String getPortugueseWord() {
        return portugueseWord;
    }

    public String getEnglishFilter() {
        return EnglishFilter;
    }

    public static Map<Integer, PetFilters> filterMap() {
        Map<Integer, PetFilters> filtersMap = new HashMap<>();
        filtersMap.put(1, new PetFilters("Nome", "Name"));
        filtersMap.put(2, new PetFilters("Tipo", "Type"));
        filtersMap.put(3, new PetFilters("Sexo", "Sex"));
        filtersMap.put(4, new PetFilters("Idade", "Age"));
        filtersMap.put(5, new PetFilters("Peso", "Weight"));
        filtersMap.put(6, new PetFilters("Raça", "Breed"));
        filtersMap.put(7, new PetFilters("Endereço", "Address"));

        return filtersMap;
    }
}
