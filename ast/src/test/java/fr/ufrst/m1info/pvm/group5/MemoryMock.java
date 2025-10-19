package fr.ufrst.m1info.pvm.group5;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;


/**
 * Static class to quickly create classes mocking Memory
 */
public class MemoryMock {

    /**
     * Creates a mock with no specific behaviour
     * @return mock created
     */
    public static Memory createEmpty(){
        return mock();
    }

    /**
     * Creates a mock that mimics memory by using a map
     * @param storage map used to store the mock data
     * @return mock created
     */
    public static Memory createWithStorage(Map<String, Value> storage){
        Memory result = createEmpty();
        doAnswer(invocation -> {
                    String arg =  invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    storage.put(arg, value);
                    return null;
                }
        ).when(result).affectValue(any(String.class), any(Object.class));

        doAnswer( invocation -> {
                    String ident = invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    storage.put(ident, value);
                    return null;
                }
        ).when(result).declVar(any(String.class), any(Value.class), any());

        doAnswer(invocation -> {
                    String identifier =  invocation.getArgument(0);
                    return storage.get(identifier);
                }
        ).when(result).val(any(String.class));
        return result;
    }

    /**
     * Creates a mock that mimics memory storage and withdrawal using a map
     * @param storage map using to store the mock data
     * @return mock created
     */
    public static Memory createWithWithdraw(Map<String, Value> storage){
        Memory result = createWithStorage(storage);
        doAnswer(invocation -> {
            storage.remove((String)invocation.getArgument(0));
           return null;
        }).when(result).withdrawDecl(any(String.class));
        return result;
    }
}
