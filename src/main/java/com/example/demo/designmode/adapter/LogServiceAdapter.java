package com.example.demo.designmode.adapter;

/**
 * 将LogService转换成PrintService
 * @author iCoderLad
 * @date 2022/08/24 23:31
 */
public class LogServiceAdapter implements PrintService {
    private LogService logService;

    public LogServiceAdapter(LogService logService){
        this.logService = logService;
    }
    @Override
    public void print() {
        logService.log();;
    }
}
