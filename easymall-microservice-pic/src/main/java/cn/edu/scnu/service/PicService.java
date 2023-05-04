package cn.edu.scnu.service;

import com.easymall.utils.UploadUtil;
import com.easymall.vo.PicUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class PicService {

    @Value("${pic.pathDirPrefix}")
    private String pathDirPrefix;

    @Value("${pic.urlPrefix}")
    private String urlPreparePrefix;

    public PicUploadResult picUpload(MultipartFile pic) {
        PicUploadResult result = new PicUploadResult();
        //1 判断后缀合法性
        String originalName = pic.getOriginalFilename();
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        if (!suffix.matches(".(jpg|png|gif|JPG|PNG|GIF)$")) {
            result.setError(1);
            return result;
        }
        //2 判断是不是木马
        try {
            BufferedImage bufImg = ImageIO.read(pic.getInputStream());
            bufImg.getWidth();
            bufImg.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }
        //3 创建以upload开始的路径
        String dir = "/" + UploadUtil.getUploadPath(originalName, "upload") + "/";

        //4 创建nginx访问的静态目录，pathDir,pathDirPrefix
        String pathDir = pathDirPrefix + dir;
        File file = new File(pathDir);
        if (!file.exists()) {
            boolean ok = file.mkdirs();
            if (!ok) {
                log.error("图片保存失败" + file.getName());
            } else {
                log.info("图片保存成功" + file.getName());
            }
        }
        //5 创建urlPrefix
        String urlPrefix = urlPreparePrefix + dir;
        //6 拼接图片名称，将图片重命名，uuid表示图片存储访问的名称
        String fileName = UUID.randomUUID() + suffix;
        //7 上传图片
        try {
            pic.transferTo(new File(pathDir + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }
        //8 返回urlPrefix+图片名称路径
        result.setUrl(urlPrefix + fileName);
        log.info("图片上传成功:{}", result.getUrl());
        return result;
    }
}
