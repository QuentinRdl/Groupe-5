package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.Heap.Heap;
import fr.ufrst.m1info.pvm.group5.memory.Heap.InsufficientMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Heap.InvalidMemoryAddressException;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    @Test
    @DisplayName("Base constructor")
    public void constructorTest() {
        Heap heap = new Heap(512);
        assertEquals(512, heap.getTotalSize());
        assertEquals(512, heap.getAvailableSize());
        assertEquals(1, heap.getBlocksSnapshot().size());
        assertFalse(heap.getBlocksSnapshot().getFirst().isAllocated());
    }

    // Allocation

    @Test
    @DisplayName("Allocate")
    public void allocateTest() {
        Heap heap = new Heap(512);
        assertEquals(1, heap.allocate(12, DataType.INT));
        assertEquals(512, heap.getTotalSize());
        assertEquals(500, heap.getAvailableSize());
        List<Heap.ElementRecord> result = List.of(
          new Heap.ElementRecord(0, true, 12, false),
          new Heap.ElementRecord(12, false, 500, true)
        );
        assertEquals(result, heap.getBlocksSnapshot());
    }

    @Test
    @DisplayName("Allocate - Not enough memory")
    public void allocateNotEnoughMemoryTest() {
        Heap heap = new Heap(512);
        assertThrows(InsufficientMemoryException.class, () -> heap.allocate(1000, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Not enough memory after allocation")
    public void allocateNotEnoughMemoryAfterAllocationTest() {
        Heap heap = new Heap(512);
        heap.allocate(500, DataType.INT);
        assertThrows(InsufficientMemoryException.class, () -> heap.allocate(500, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Invalid size")
    public void allocateInvalidSizeTest() {
        Heap heap = new Heap(512);
        assertThrows(IllegalArgumentException.class, () -> heap.allocate(-1, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Multiple")
    public void allocateMultipleTest() {
        Heap heap = new Heap(512);
        List<Heap.ElementRecord> result = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            heap.allocate(50,  DataType.INT);
            result.add(new Heap.ElementRecord(i * 50,true,50,false));
        }
        result.add(new Heap.ElementRecord(500,false,12,true));
        assertEquals(result,heap.getBlocksSnapshot());
    }

    // Free
    @Test
    @DisplayName("Free - Single")
    public void freeTest() {
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.free(address);
        assertEquals(512, heap.getTotalSize());
        assertEquals(512, heap.getAvailableSize());
        assertEquals(new Heap.ElementRecord(0, false, 512, true), heap.getBlocksSnapshot().getFirst());
    }

    @Test
    @DisplayName("Free - InvalidAddress")
    public void freeInvalidAddressTest() {
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        assertThrows(InvalidMemoryAddressException.class, () -> heap.free(address+1));
    }


    @Test
    @DisplayName("Free - In the middle")
    public void freeInTheMiddleTest() {
        Heap heap = new Heap(512);
        int b1 = heap.allocate(50,DataType.INT);
        int b2 = heap.allocate(50,DataType.INT);
        heap.free(b1);
        assertEquals(3, heap.getBlocksSnapshot().size());
        heap.free(b2);
        assertEquals(1, heap.getBlocksSnapshot().size());
    }

    @Test
    @DisplayName("Free - Multiple")
    public void freeMultipleTest() throws Exception {
        Heap heap = new Heap(512);
        List<Integer> addresses =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            addresses.add(heap.allocate(50, DataType.INT));
        }
        assertEquals(12, heap.getAvailableSize());
        for(int i = 1; i <= 10; i++){
            heap.free(addresses.get(i-1));
            assertEquals(12 + 50 * i, heap.getAvailableSize());
            if(i<10) // Each block only does 1 merge except the last
                assertEquals(12-i, heap.getBlocksSnapshot().size());
        }
        assertEquals(1, heap.getBlocksSnapshot().size());
    }

    // References

}
