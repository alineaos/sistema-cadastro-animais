package shelter.animal.repository;

import shelter.animal.exceptions.PetValidateException;
import shelter.animal.models.Address;
import shelter.animal.models.Pet;

import java.util.regex.Pattern;

public class ValidateRepository {
    public final static String NAO_INFORMADO = "NÃO INFORMADO";
    public final static Pattern REGEX = Pattern.compile("([a-zA-z\\s]+|)");

    public String defaultIfEmpty(String line) {
        return (line.isBlank()) ? NAO_INFORMADO : line;
    }

    public String validateName(String name) {
        if (!REGEX.matcher(name).matches()) {
            throw new PetValidateException("Não é permitido o uso de números ou caracteres especiais.");
        }

        return defaultIfEmpty(name);
    }

    public boolean validateType(String type) {
        if (!type.equalsIgnoreCase("gato") && !type.equalsIgnoreCase("cachorro")) {
            System.out.println("Opção inválida, precisa ser Cachorro ou Gato");
            return false;
        }
        return true;
    }

    public boolean validateSex(String sex) {
        if (!sex.equalsIgnoreCase("f") && !sex.equalsIgnoreCase("m")) {
            System.out.println("Opção inválida, precisa ser F ou M");
            return false;
        }
        return true;
    }

    public boolean validateStreet(String street) {
        if (street.isEmpty()) {
            System.out.println("O nome da rua não pode estar em branco.");
            return false;
        }
        return true;
    }

    public boolean validateCity(String city) {
        if (city.isEmpty()) {
            System.out.println("O nome da cidade não pode estar em branco.");
            return false;
        }
        return true;
    }

    public boolean validateDate(String date) {
        if (!date.equalsIgnoreCase("meses") && !date.equalsIgnoreCase("anos") && !date.isEmpty()) {
            System.out.println("Opção inválida, precisa ser em meses ou anos");
            return false;
        }
        return true;
    }

    public Double validateAge(String auxAge) {
        double age = Double.parseDouble(auxAge);
        if (age > 20) {
            throw new PetValidateException("A idade não pode ser maior que 20 anos.");
        }
        return age;
    }

    public Double validateWeight(String auxWeight) {
        double weight = Double.parseDouble(auxWeight);
        if (weight < 0.5 || weight > 60) {
            throw new PetValidateException("O peso precisa estar entre 0.5kg e 60kg.");
        }
        return weight;
    }

    public String validateBreed(String breed) {
        if (!REGEX.matcher(breed).matches()) {
            throw new PetValidateException("Não é permitido o uso de números ou caracteres especiais.");
        }
        return defaultIfEmpty(breed);
    }

    public Double stringToAge(String age) {
        if (age.equalsIgnoreCase("NÃO INFORMADO")) {
            return null;
        }
        return Double.parseDouble(age.replace(" anos", "").replace(",", "."));
    }

    public Double stringToWeight(String age) {
        if (age.equalsIgnoreCase("NÃO INFORMADO")) {
            return null;
        }
        return Double.parseDouble(age.replace("kg", "").replace(",", "."));
    }

    public boolean petMatchesFilters(Pet pet, String key, String value) {
        return switch (key) {
            case "Name" -> pet.getName().toLowerCase().contains(value.toLowerCase().toLowerCase());
            case "Type" -> pet.getType().getClassification().toLowerCase().contains(value.toLowerCase());
            case "Sex" -> pet.getSex().getClassification().toLowerCase().contains(value.toLowerCase());
            case "Age" -> pet.getAge() != null && pet.getAge().toString().toLowerCase().contains(value.toLowerCase());
            case "Weight" ->
                    pet.getWeight() != null && pet.getWeight().toString().toLowerCase().contains(value.toLowerCase());
            case "Breed" -> pet.getBreed().toLowerCase().contains(value.toLowerCase());
            case "Address" -> pet.getAddress().toString().toLowerCase().contains(value.toLowerCase());
            default -> false;
        };
    }

    public Pet dataToUpdate(Pet petToUpdate, int option, String newData) {
        switch (option) {
            case 1:
                validateName(newData);
                petToUpdate.setName(newData);
                break;
            case 2:
                validateAge(newData);
                petToUpdate.setAge(Double.parseDouble(newData));
                break;
            case 3:
                validateWeight(newData);
                petToUpdate.setWeight(Double.parseDouble(newData));
                break;
            case 4:
                validateBreed(newData);
                petToUpdate.setBreed(newData);
                break;
            case 5:
                String[] stringToAddress = petToUpdate.getAddress().toString().split(",");
                validateStreet(stringToAddress[0]);
                stringToAddress[1] = defaultIfEmpty(stringToAddress[1]);
                validateCity(stringToAddress[2]);
                Address newAddress = new Address(stringToAddress[0], stringToAddress[1], stringToAddress[2]);
                petToUpdate.setAddress(newAddress);
        }
        return petToUpdate;
    }
}
