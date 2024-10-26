import com.online.automobile.controller.ApiController;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiController.class)
public class test {
    @MockBean
    private ApiController apiController;

    @Test
    public void getAllServiceTypes(HttpServletRequest request){
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject object = new JSONObject();
        object.put("id", 1);
        object.put("description", "test_description");

        JSONObject object2 = new JSONObject();
        object2.put("id", 2);
        object2.put("description", "test_description 2");

        list.add(object);

        Mockito.when(apiController.getAllServiceTypes(request)).thenReturn(list);
        List<JSONObject> allServiceTypes = apiController.getAllServiceTypes(request);
        assertEquals(list,allServiceTypes);

    }
}
