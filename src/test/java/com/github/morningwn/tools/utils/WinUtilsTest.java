package com.github.morningwn.tools.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

class WinUtilsTest {

    @Test
    void setWallpjaaper() {
        File file = new File("tmp/1.jpg");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
//        WinUtils.setWallpaper(file.getAbsolutePath());

    }

    @Test
    public void test() {
        System.out.println(System.getProperty("user.home"));
    }

//    @Test
//    public void SystemInfo() {
//        SystemInfo systemInfo = new SystemInfo();
//        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
//        HardwareAbstractionLayer hardware = systemInfo.getHardware();
//        GlobalMemory memory = hardware.getMemory();
//        System.out.println("可用内存" + memory.getAvailable());
//
//        CentralProcessor processor = hardware.getProcessor();
//        long[] currentFreq = processor.getCurrentFreq();
//        System.out.println(Arrays.toString(currentFreq));
//
//        Sensors sensors = hardware.getSensors();
//        System.out.println("CPU温度" + sensors.getCpuTemperature());
//        System.out.println("风扇转速" + Arrays.toString(sensors.getFanSpeeds()));
//        System.out.println("CPU电压" + sensors.getCpuVoltage());
//
//
//        List<PowerSource> powerSources = hardware.getPowerSources();
//        for (PowerSource powerSource : powerSources) {
//            System.out.println();
//            System.out.println(powerSource.getDeviceName());
//            System.out.println("当前容量" + powerSource.getCurrentCapacity());
//            System.out.println("设计容量" + powerSource.getDesignCapacity());
//            System.out.println("最大容量" + powerSource.getMaxCapacity());
//
//            System.out.println("电池温度" + powerSource.getTemperature());
//            System.out.println();
//        }
//
//        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
//        for (GraphicsCard graphicsCard : graphicsCards) {
//            System.out.println();
//            System.out.println(graphicsCard.getDeviceId());
//            System.out.println(graphicsCard.getName());
//            System.out.println(graphicsCard.getVRam());
//            System.out.println();
//        }
//    }
//
}