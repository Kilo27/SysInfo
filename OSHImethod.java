import oshi.SystemInfo;
import oshi.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.LogicalProcessor;
import oshi.hardware.CentralProcessor.PhysicalProcessor;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.hardware.CentralProcessor.ProcessorCache;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.UsbDevice;
import oshi.hardware.VirtualMemory;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.PhysicalMemory;
import java.util.*;


class OSHImethod {
    
    public void initSystemInfo() {
        SystemInfo systeminfo = new SystemInfo();
    }

    public void initHardware() {
        initSystemInfo();
        HardwareAbstractionLayer hardware = systeminfo.getHardware();
    }

    public void initCentralProcessor() {
        initHardware();
        CentralProcessor cpu = hardware.getProcessor();
    }

    public void initGlobalMemory() {
        initHardware();
        GlobalMemory memory = hardware.getMemory();
    }

    public void initProcessorCache() {
        initCentralProcessor();
        List<ProcessorCache> cache = cpu.getProcessorCaches();
    }

    public void initLogicalProcessor() {
        initCentralProcessor();
        List<LogicalProcessor> logprocs = cpu.getLogicalProcessors();
    }

    public void initPhysicalProcessor() {
        initCentralProcessor();
        List<PhysicalProcessor> physprocs =  cpu.getPhysicalProcessors();
    }

    public void initSystemUtil() {
        initSystemInfo();
        initHardware();
        initCentralProcessor();
        initGlobalMemory();
        initLogicalProcessor();
        initPhysicalProcessor();
    }  
}
