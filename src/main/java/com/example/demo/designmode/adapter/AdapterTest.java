package com.example.demo.designmode.adapter;

/**
 * @author iCoderLad
 * @date 2022/08/24 23:35
 */
public class AdapterTest {
    public static void main(String[] args) {
        PrintService printService = new PrintServiceImpl();
        testPrint(printService);

        LogService logService = new LogServiceImpl();
        PrintService logServiceAdapter = new LogServiceAdapter(logService);
        testPrint(logServiceAdapter);
    }

    static void testPrint(PrintService printService){
        printService.print();
    }
}
