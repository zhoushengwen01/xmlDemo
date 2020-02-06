import java.util.List;

public class Person {
    private String name;
    private String age;
    private String gender;
    private List<Person> sons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Person> getSons() {
        return sons;
    }

    public void setSons(List<Person> sons) {
        this.sons = sons;
    }


}
