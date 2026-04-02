package shelter.animal.models.enums;

public enum PetSex {
    FEMEA("F", "Femea"),
    MACHO("M", "Macho");

    private String abbreaviation;
    private String classification;

    PetSex(String abbreaviation, String classification) {
        this.abbreaviation = abbreaviation;
        this.classification = classification;
    }

    public String getAbbreaviation() {
        return abbreaviation;
    }

    public String getClassification() {
        return classification;
    }

    public static PetSex selectSex(String abbreaviation){
        for (PetSex petSex : PetSex.values()){
            if(petSex.abbreaviation.equalsIgnoreCase(abbreaviation)){
                return petSex;
            }
        }
        return null;
    }
}
