import oshi.SystemInfo;
import oshi.hardware.Sensors;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.*;
import oshi.hardware.UsbDevice;
import oshi.util.FormatUtil;

import java.util.List;

class SysInfo {

    private SystemInfo systeminfo;
    private HardwareAbstractionLayer hardware;
    private CentralProcessor cpu;
    private GlobalMemory memory;
    

    public SysInfo() {
        initSystemInfo();
        initHardware();
        initCentralProcessor();
        //initMemory();
        //initSensors();
    }

    public void initSystemInfo() {
        systeminfo = new SystemInfo();
    }
    public void initHardware() {
       hardware = systeminfo.getHardware();

    }
    public void initCentralProcessor() {
        cpu = hardware.getProcessor();
    }
    public void initMemory() {
        memory = hardware.getMemory();
    }

    public void initSensors() {
        Sensors sensor = hardware.getSensors();
    }
    
    public void printSystemSpecifics() {
        System.out.print("\n=== System Specifications ===");
        System.out.printf("%-25s | %s\n ", "Name", cpu.getProcessorIdentifier().getName());
        System.out.printf("%-25s | %s\n ", "Family", cpu.getProcessorIdentifier().getFamily());
        System.out.printf("%-25s | %s\n " ,"Model", cpu.getProcessorIdentifier().getModel());
        System.out.printf("%-25s | %s\n ", "Processor ID: ", cpu.getProcessorIdentifier().getProcessorID());
        System.out.printf("%-25s | %s\n", "Is Model 64 bit: ", cpu.getProcessorIdentifier().isCpu64bit());
        System.out.printf("%-25s | %s\n", "Microarchitecture: ", cpu.getProcessorIdentifier().getMicroarchitecture());
    }

    public void printCPU() {
        long[] freqs = cpu.getCurrentFreq();
        long maxfreq = cpu.getMaxFreq();
        double[] cpuLoad = cpu.getProcessorCpuLoad(1000);
        List<PhysicalProcessor> phyproc = cpu.getPhysicalProcessors();
        List<LogicalProcessor> logproc = cpu.getLogicalProcessors();
        System.out.println("\n=== CPU ===");
        System.out.println("");
        System.out.print("Physical Processors: ");
        System.out.printf("%-25s | %s\n", "Physical Processor Count: ", cpu.getPhysicalProcessorCount());

        for (PhysicalProcessor proc : phyproc) {
            System.out.printf("%-25s | %s\n","Processor ID: " , proc.getIdString());
            System.out.printf("%-25s | %s\n","Processor Package Number: " , proc.getPhysicalPackageNumber());
            System.out.printf("%-25s | %s\n","Processor Core ID:" , proc.getPhysicalProcessorNumber());
            System.out.printf("%-25s | %s\n", "Processor Efficiency: " , proc.getEfficiency());
        }
        System.out.println("Logical Processors: ");
        System.out.printf("%-25s | %s\n", " Logical Processor Count: ", cpu.getLogicalProcessorCount());

        for(LogicalProcessor lproc : logproc) {
            System.out.printf("%-25s | %s\n", "NUMA Node: " , lproc.getNumaNode());
            System.out.printf("%-25s | %s\n", "Package Number: " , lproc.getPhysicalPackageNumber());
            System.out.printf("%-25s | %s\n","Processor Number: " , lproc.getPhysicalProcessorNumber());
            System.out.printf("%-25s | %s\n","Processor Group", lproc.getProcessorGroup());
            System.out.printf("%-25s | %s\n","Processor Number", lproc.getProcessorNumber());
        }
        System.out.print("Current Frequency: ");
        for(long freq : freqs) {
            System.out.printf("%-25s | %d\n", "Frequency", FormatUtil.formatHertz(freq));
        }
        System.out.printf("%-25s | %d\n", "Maximum Frequency: ", FormatUtil.formatHertz(maxfreq));
        System.out.println("Ticks: ");
        for(double tick : cpuLoad) {
            System.out.println(tick);
        }
    }
   //%-25s | %s\n 
    public void printMemory() {
        System.out.print("\n=== Memory ===");
        System.out.printf("%-25s | %s\n","Total Memory: " , memory.getTotal());
        System.out.printf("%-25s | %s\n","Available Memory: " , memory.getAvailable());
        System.out.printf("%-25s | %s\n","Virtual Memory: " , memory.getVirtualMemory());
        System.out.printf("%-25s | %s\n","Page Size: " , memory.getPageSize());
    }

    public void printCacheMemory() {
        System.out.print("\n=== Cache Memory ===");
        List<ProcessorCache> caches = cpu.getProcessorCaches();

        for (ProcessorCache cache : caches) {
            System.out.printf("%-25s | %s\n","Cache Level: " , cache.getLevel());
            System.out.printf("%-25s | %s\n","Cache Type: " , cache.getType());
            System.out.printf("%-25s | %s\n","Cache Size: " , FormatUtil.formatBytes(cache.getCacheSize()));
            System.out.printf("%-25s | %s\n","Cache Line Size: " , FormatUtil.formatBytes(cache.getLineSize()));
            System.out.printf("%-25s | %s\n","Cache Associativity: " , cache.getAssociativity());
            System.out.print("====");
        }
    }
    
    public void printUSBdevices() {
        List<UsbDevice> devices = hardware.getUsbDevices(true);        
        System.out.println("\n=== USB Devices ===");
        System.out.println("Devices:");
        for(UsbDevice device : devices) {
            System.out.printf("%-25s | %s\n", "Name: ", device.getName());
            System.out.printf("%-25s | %s\n", "Product ID: ", device.getProductId());
            System.out.printf("%-25s | %s\n", "Serial Number: ", device.getSerialNumber() == null ? "N/A" : device.getSerialNumber());
            System.out.printf("%-25s | %s\n", "Unique Device ID: ", device.getUniqueDeviceId());
            System.out.printf("%-25s | %s\n", "Vendor: ", device.getVendor());  
            System.out.printf("%-25s | %s\n", "Vendor ID: ", device.getVendorId());  
        }
    }
       
    public void printSysUtil() {
        printSystemSpecifics();
        printCPU();
        printMemory();
        printCacheMemory();
    }
    
}
/* Used Chatgpt to check for any logic issues or improvements to current methods. -May implement this later
 public void printUSBTree(UsbDevice device, int indent) {
    String prefix = " ".repeat(indent * 2);
    System.out.printf("%s%s (Vendor: %s, Product: %s)\n",
            prefix, device.getName(), device.getVendor(), device.getProductId());
    for (UsbDevice child : device.getConnectedDevices()) {
        printUSBTree(child, indent + 1);
    }
}

// Usage:
List<UsbDevice> devices = hardware.getUsbDevices(true);
for (UsbDevice device : devices) {
    printUSBTree(device, 0);
}*/
