package site.metacoding.white.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
class Dog {
    private Integer id;
    private String name;
}

@Setter
@Getter
class DogDto {
    private Integer id;
    private String name;
}

public class MapperTest2 {

    @Test
    public void convert_test() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog(1, "강아지"));
        dogList.add(new Dog(2, "고양이"));

        // dogList -> List<DogDto>
        List<DogDto> dogDtoList = new ArrayList<>();

        for (Dog dog : dogList) {
            DogDto dogDto = new DogDto();
            dogDto.setId(dog.getId());
            dogDto.setName(dog.getName());
            dogDtoList.add(dogDto);
        }

    }
}