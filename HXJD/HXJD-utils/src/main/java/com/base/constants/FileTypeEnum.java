package com.base.constants;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: FileTypeEnum
 * @包名: com.base.constants
 * @描述: 上传文件类型
 * @所属: 华夏九鼎
 * @日期: 2017/6/2 14:36
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public enum  FileTypeEnum {
//    public static String[]fileType={"image","video","audio","otherfile"};
    IMAGE(1, "image"),
    VIDEO(2, "video"),//视频
    AUDIO(3, "audio"),//语音
    OTHER(4, "document"),//文档
    UNKNOWN(5,"table");//表格


    private int value;
    private String fileType;

    FileTypeEnum(int value, String fileType) {
        this.value = value;
        this.fileType = fileType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFileType() {
        return fileType;
    }

    public static String getFileTypeByValue(int value){
        for(FileTypeEnum fileTypeEnum:FileTypeEnum.values()){
            if(fileTypeEnum.getValue()==value){
                return fileTypeEnum.getFileType();
            }
        }
        return UNKNOWN.getFileType();
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }



}