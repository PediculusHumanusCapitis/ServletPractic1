package newapline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static final Model instance = new Model();

    private final Map<Integer, User> model;

    public static Model getInstance(){
        return instance;
    }

    private  Model(){
        model = new HashMap<>();

        model.put(1,new User("Anton","Antonov",1000));
        model.put(2,new User("Kolia","Kolianov",500));
        model.put(3,new User("Sania","Sanich",2));
        model.put(4,new User("Natalia","Ivanova",200));
    }

    public void add(User user, int id){
        model.put(id, user);
    }
    public void delete(int id){
        model.remove(id);
        Map<Integer, User> newModel = new HashMap<>();
        int newId = 1;
        for (User user : model.values()) {
            newModel.put(newId++, user);
        }
        model.clear();
        model.putAll(newModel);
    }

    public void update(int id, String name, String surname, double salary){
        User user = model.get(id);
        user.setName(name);
        user.setSurname(surname);
        user.setSalary(salary);
    }
    public Map<Integer,User> getFromList(){
        return model;
    }

}
