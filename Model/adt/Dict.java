package Model.adt;
import java.util.HashMap;
import java.util.Map;


public class Dict<T1,T2> implements IDict<T1,T2> {
    private final HashMap<T1, T2> dict;

    public Dict() {
        dict = new HashMap<>();
    }

    public Dict(Dict<T1,T2> anotherDict){
        try{
            dict = (HashMap<T1, T2>) anotherDict.dict.clone();
        } catch (Exception e) {
            throw new RuntimeException("copy constructor error");
        }
    }

    @Override
    public void add(T1 v1, T2 v2) { dict.put(v1,v2); }

    @Override
    public void update(T1 v1, T2 v2) { dict.replace(v1, v2); }

    @Override
    public void remove(T1 v1) {
        dict.remove(v1);
    }

    @Override
    public T2 lookup(T1 id) { return dict.get(id); }

    @Override
    public boolean isDefined(T1 id){ return dict.containsKey(id); }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Map.Entry<T1, T2> entry : dict.entrySet()) {
            T1 key = entry.getKey();
            output.append(key.toString());
            output.append("=");
            T2 value = entry.getValue();
            output.append(value.toString());
            output.append("  ");
        }
        return output.toString();
    }

    @Override
    public Map<T1, T2> getContent() { return this.dict; }
}
