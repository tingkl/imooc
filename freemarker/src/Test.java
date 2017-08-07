import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tingkl on 2017/8/7.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);


        Map root = new HashMap();
        root.put("user", "Big Joe");
        Template template = cfg.getTemplate("test.ftlh");


    }
}
