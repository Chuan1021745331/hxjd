import com.base.constants.FileTypeEnum;
import org.junit.Test;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: PACKAGE_NAME
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/6/6 14:55
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class FileTypeEnumTest {
    private final static Logger logger=LoggerFactory.getLogger(IndexController.class);

    @Test
    public void testGetFileType()
    {
//        FileTypeEnum fileTypeEnum = FileTypeEnum.AUDIO;

//        logger.info(fileTypeEnum.getFileType());
        logger.info(FileTypeEnum.getFileTypeByValue(0));
        logger.info(FileTypeEnum.getFileTypeByValue(1));
        logger.info(FileTypeEnum.getFileTypeByValue(2));
        logger.info(FileTypeEnum.getFileTypeByValue(3));
        logger.info(FileTypeEnum.getFileTypeByValue(4));
    }
}
