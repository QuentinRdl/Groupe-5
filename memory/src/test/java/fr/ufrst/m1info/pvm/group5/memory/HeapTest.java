package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.Heap.Heap;
import fr.ufrst.m1info.pvm.group5.memory.Heap.InsufficientMemoryException;
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


}
