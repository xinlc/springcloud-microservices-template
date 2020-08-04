package com.example.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EasyExcelTemplateUtil {

    public static void readExcel(Class<Objects> tClass, String fileName) {
        //读excel
        // 构造监听器 有个很重要的点 Listener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, T.class, new EasyExcelListenerUtil()).sheet().doRead();
    }

    public static void simpleWrite(T tClass, String fileName) {
        EasyExcel.write(fileName, T.class).sheet(fileName).doWrite(new ArrayList());
    }

    /**
     * 简单填充excel
     */
    public static void simpleFill(String templateFileName, String fileName, Map<String, Object> map) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 根据Map填充
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    /**
     * 填充列表
     *
     * @since 2.1.1
     */
    public static void listFill(String templateFileName, String fileName, List<Object> list) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(list);
    }

    /**
     * 复杂的填充
     */

    public static ResponseEntity<byte[]> complexFill(InputStream TemplateInputStream, Object data, Map<String, Object> map) throws IOException, InvalidFormatException {
        // 模板注意 用{} 来表示你要用的变量
        // {} 代表普通变量 {.} 代表是list的变量
        // String file = "E:\\excel6.xlsx";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(byteArrayOutputStream).withTemplate(TemplateInputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setTemplateInputStream(TemplateInputStream);
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 ,
        // 会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data, fillConfig, writeSheet);
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
        byteArrayOutputStream.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> filebyte = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
        return filebyte;
    }

    /**
     * 横向的填充
     */
    public static ResponseEntity<byte[]> horizontalFill(InputStream TemplateInputStream, List<Object> list, Map<String, Object> map) throws IOException {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        //String file = "D:\\excel6.xlsx";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(byteArrayOutputStream).withTemplate(TemplateInputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        excelWriter.fill(list, fillConfig, writeSheet);
        excelWriter.fill(map, writeSheet);
        // 别忘记关闭流
        excelWriter.finish();
        byteArrayOutputStream.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> filebyte = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
        return filebyte;
    }

    /**
     * 导出模板
     */
    public static ResponseEntity<byte[]> exportTemplate(InputStream TemplateInputStream) throws IOException {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        // String file = "E:\\excel6.xlsx";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(byteArrayOutputStream).withTemplate(TemplateInputStream).build();
        // 别忘记关闭流
        excelWriter.finish();
        byteArrayOutputStream.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> filebyte = new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
        return filebyte;
    }

}


