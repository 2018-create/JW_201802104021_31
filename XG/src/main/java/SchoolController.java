import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/school.ctl")
public class SchoolController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有对象，否则响应id指定的对象
            if (id_str == null) {
                responseSchools(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseSchool(id, response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    //响应一个对象
    private void responseSchool(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //根据id查找
        School school = SchoolService.getInstance().find(id);
        String school_json = JSON.toJSONString(school);
        //控制台打印结果
        System.out.println(school_json);
        //浏览器展示结果
        response.getWriter().println(school_json);
    }
    //响应所有对象
    private void responseSchools(HttpServletResponse response)
            throws ServletException, IOException,SQLException {
        //获得所有
        Collection<School> schools = SchoolService.getInstance().findAll();
        String schools_json = JSON.toJSONString(schools);
        //控制台打印结果
        System.out.println(schools_json);
        //浏览器展示结果
        response.getWriter().println(schools_json);
    }
}
