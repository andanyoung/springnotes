package cn.andyoung;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
  public String index() {
    return "index";
  }
  /**
   * 文件上传
   *
   * @return
   */
  @RequestMapping("/fileupload1")
  public String fileuoload1(HttpServletRequest request) throws Exception {
    System.out.println("文件上传...");

    // 使用fileupload组件完成文件上传
    // 上传的位置
    String path = request.getSession().getServletContext().getRealPath("/uploads/");
    // 判断，该路径是否存在
    File file = new File(path);
    if (!file.exists()) {
      // 创建该文件夹
      file.mkdirs();
    }

    // 解析request对象，获取上传文件项
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    // 解析request
    List<FileItem> items = upload.parseRequest(request);
    // 遍历
    for (FileItem item : items) {
      // 进行判断，当前item对象是否是上传文件项
      if (item.isFormField()) {
        // 说明普通表单向
      } else {
        // 说明上传文件项
        // 获取上传文件的名称
        String filename = item.getName();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;
        // 完成文件上传
        item.write(new File(path, filename));
        // 删除临时文件
        item.delete();
      }
    }

    return "success";
  }
}
