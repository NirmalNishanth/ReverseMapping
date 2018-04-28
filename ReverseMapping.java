
package reversemapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author nishanth
 */
public class ReverseMapping {

    private final Map<String, List<String>> map = new ConcurrentHashMap<String, List<String>>();
    private final Map<String, List<String>> reverseMap = new ConcurrentHashMap<String, List<String>>();
    private String keyProperty;
    private String valueProperty;

    public List<String> getMappedValues(String mappingKey) {
        return getValues(false, mappingKey);
    }

    public List<String> getReverseMappedValues(String mappingKey) {
        return getValues(true, mappingKey);
    }

    private List<String> getValues(boolean isReverse, String mappingKey) {
        Map<String, List<String>> inputMap = isReverse ? reverseMap : map;

        List<String> list = inputMap.get(mappingKey);
        if (list == null) {
            list = Collections.emptyList();
        }
        return new ArrayList<String>(new HashSet<String>(list));
    }

    public String getMappedValue(String mappingKey) {
        return getValue(false, mappingKey);
    }

    public String getReverseMappedValue(String mappingKey) {
        return getValue(true, mappingKey);
    }

    private String getValue(boolean isReverse, String mappingKey) {
        Map<String, List<String>> inputMap = isReverse ? reverseMap : map;
        String property = isReverse ? keyProperty : valueProperty;

        List<String> list = inputMap.get(mappingKey);

        return list.get(0);
    }

    private void deleteFromMap(boolean isReverse, String mappingKey, String mappingValue) {
        Map<String, List<String>> inputMap = isReverse ? reverseMap : map;

        List<String> list = inputMap.get(mappingKey);
        if (list == null) {
            return;
        }

        list.remove(mappingValue);
        if (list.isEmpty()) {
            inputMap.remove(mappingKey);
        }
    }
    
    public enum op{
        data,
        one
    }

    private void addInMap(boolean isReverse, String mappingKey, String mappingValue) {
        Map<String, List<String>> inputMap = isReverse ? reverseMap : map;

        List<String> list = inputMap.get(mappingKey);
        if (list == null) {
            list = new ArrayList<String>();
            inputMap.put(mappingKey, list);
        }

        list.add(mappingValue);
    }

    public Set<String> getAllMappingKeys() {
        return map.keySet();
    }

    public Set<String> getAllReverseMappingKeys() {
        return reverseMap.keySet();
    }

    private void delete(String mappingKey, String mappingValue) {
        deleteFromMap(false, mappingKey, mappingValue);
        deleteFromMap(true, mappingValue, mappingKey);
    }

    private void add(String mappingKey, String mappingValue) {

        addInMap(false, mappingKey, mappingValue);
        addInMap(true, mappingValue, mappingKey);
    }

    public static void main(String[] args) {
        ReverseMapping revMap = new ReverseMapping();
        
        System.out.println("====1=====");
        System.out.println(revMap.map.size());
        System.out.println(revMap.reverseMap.size());
        
        revMap.add("1","nishanth");
        revMap.add("2","bala");
        
        System.out.println("====2=====");
        System.out.println(revMap.map.size());
        System.out.println(revMap.reverseMap.size());
    
        
        revMap.delete("1","nishanth");
        System.out.println("====3=====");
        System.out.println(revMap.map.size());
        System.out.println(revMap.reverseMap.size());
     
    }

}
